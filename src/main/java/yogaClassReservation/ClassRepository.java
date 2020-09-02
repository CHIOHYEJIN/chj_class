package yogaClassReservation;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClassRepository extends PagingAndSortingRepository<Class, Long>{

    List<Class> findByClassId(Long classId);
}