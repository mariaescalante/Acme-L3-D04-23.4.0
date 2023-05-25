
package acme.features.lecturer.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.CourseType;
import acme.entities.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("SELECT l FROM Lecture l WHERE l.lecturer.id = :id")
	Collection<Lecture> findManyLecturesByLecturerId(int id);

	@Query("SELECT l.time FROM Lecture l WHERE l.lecturer.id = :id")
	Collection<Integer> findManyLecturesTimeByLecturerId(int id);

	@Query("SELECT m.lecture.time FROM Membership m WHERE m.course.id = :id")
	Collection<Integer> findManyLecturesTimeByCourseId(int id);

	@Query("SELECT count(l) FROM Lecture l WHERE l.lecturer.id = :id")
	Integer findTotalLecturesByLecturerId(int id);

	@Query("SELECT count(c) FROM Course c WHERE c.lecturer.id = :id")
	Integer findTotalCoursesByLecturerId(int id);

	@Query("SELECT c FROM Course c WHERE c.lecturer.id = :id")
	Collection<Course> findManyCoursesByLecturerId(int id);

	@Query("SELECT count(l) FROM Lecture l WHERE l.lecturer.id = :id and l.theoreticalOrHandsOn = :ct")
	Integer totalCourseTypeLectures(int id, CourseType ct);

	default Double averageLectureLearningTime(final int id) {
		Double res;
		final Collection<Integer> ts = this.findManyLecturesTimeByLecturerId(id);
		final Integer total = this.findTotalLecturesByLecturerId(id);
		if (total == 0)
			return null;
		res = 0.0;
		for (final Integer t : ts)
			res += t;
		return res / total;
	}

	default Integer lectureLearningTimeMinimum(final int id) {
		Integer res;
		final Collection<Integer> ts = this.findManyLecturesTimeByLecturerId(id);
		if (ts.isEmpty())
			return null;
		res = Integer.MAX_VALUE;
		for (final Integer t : ts)
			if (t < res)
				res = t;
		return res;
	}

	default Integer lectureLearningTimeMaximum(final int id) {
		Integer res;
		final Collection<Integer> ts = this.findManyLecturesTimeByLecturerId(id);
		if (ts.isEmpty())
			return null;
		res = Integer.MIN_VALUE;
		for (final Integer t : ts)
			if (t > res)
				res = t;
		return res;
	}

	default Double lectureLearningTimeDeviation(final int id) {
		final Collection<Integer> ls = this.findManyLecturesTimeByLecturerId(id);
		final Integer total = this.findTotalLecturesByLecturerId(id);
		if (ls.size() < 2)
			return null;
		final Double avg = this.averageLectureLearningTime(id);
		final Double sum = ls.stream().mapToDouble(x -> (x - avg) * (x - avg)).sum();
		return Math.sqrt(sum / total);
	}

	default Double averageCourseLearningTime(final int id) {
		final Collection<Course> cs = this.findManyCoursesByLecturerId(id);
		final Integer total = this.findTotalCoursesByLecturerId(id);
		Double res;
		List<Integer> ts;
		if (total == 0)
			return null;
		res = 0.0;
		for (final Course c : cs) {
			ts = new ArrayList<>(this.findManyLecturesTimeByCourseId(c.getId()));
			for (final Integer t : ts)
				res += t;
		}
		return res / total;
	}
	default Integer courseLearningTimeMinimum(final int id) {
		final Collection<Course> cs = this.findManyCoursesByLecturerId(id);
		Integer res;
		final List<Integer> ts;
		if (cs.isEmpty())
			return null;
		res = Integer.MAX_VALUE;
		for (final Course c : cs) {
			Integer totalPerCourse;
			final Collection<Integer> ls = this.findManyLecturesTimeByCourseId(c.getId());
			if (!ls.isEmpty()) {
				totalPerCourse = 0;
				for (final Integer l : ls)
					totalPerCourse = totalPerCourse + l;
				if (totalPerCourse < res)
					res = totalPerCourse;
			} else
				return 0;
		}
		return res;
	}
	default Integer courseLearningTimeMaximum(final int id) {
		final Collection<Course> cs = this.findManyCoursesByLecturerId(id);
		Integer res;
		final List<Integer> ts;
		if (cs.isEmpty())
			return null;
		res = Integer.MIN_VALUE;
		for (final Course c : cs) {
			Integer totalPerCourse;
			final Collection<Integer> ls = this.findManyLecturesTimeByCourseId(c.getId());
			if (!ls.isEmpty()) {
				totalPerCourse = 0;
				for (final Integer l : ls)
					totalPerCourse = totalPerCourse + l;
				if (totalPerCourse > res)
					res = totalPerCourse;
			}
		}
		return res;
	}
	default Double courseLearningTimeDeviation(final int id) {
		final Collection<Course> cs = this.findManyCoursesByLecturerId(id);
		final List<Integer> ts = new ArrayList<>();
		final Integer total = this.findTotalCoursesByLecturerId(id);
		for (final Course c : cs) {
			Integer totalPerCourse;
			final Collection<Integer> ls = this.findManyLecturesTimeByCourseId(c.getId());
			totalPerCourse = 0;
			for (final Integer l : ls)
				totalPerCourse = totalPerCourse + l;
			ts.add(totalPerCourse);
		}
		if (ts.size() < 2)
			return null;
		final Double avg = this.averageCourseLearningTime(id);
		final Double numerator = ts.stream().mapToDouble(x -> (x - avg) * (x - avg)).sum();
		return Math.sqrt(numerator / total);
	}

}
