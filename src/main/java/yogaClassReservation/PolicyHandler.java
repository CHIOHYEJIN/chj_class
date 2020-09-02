package yogaClassReservation;

import yogaClassReservation.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    ClassRepository classRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_ChangeRemaining(@Payload Reserved reserved){

        if(reserved.isMe()){
            System.out.println("##### listener ChangeRemaining : " + reserved.toJson());

            List<Class> yogaClassList = classRepository.findByClassId(reserved.getClassId());

            for(Class yogaClass : yogaClassList)
            {
                // 예약이 추가 되면, 수강 정원을 - 1 씩 감소한다.
                yogaClass.setClassStock(yogaClass.getClassStock() - 1);
                yogaClass.setReserveId(reserved.getReserveId());

                // 남은 수강 정원이 0보다 작아지는 경우(수강 정원이 초과한 경우), outOfClass Event를 발행한다.
                if(yogaClass.getClassStock() < 0 )
                {
                    System.out.println("classOutOfStock Event");

                    OutOfClass outOfClass = new OutOfClass();
                    outOfClass.setId(reserved.getId());
                    outOfClass.setReserveId(reserved.getReserveId());
                    outOfClass.setClassId(reserved.getClassId());
                    outOfClass.setClassStock(yogaClass.getClassStock());

                    classRepository.save(yogaClass);

                    //outOfClass.publishAfterCommit();
                    outOfClass.publish();
                }
                else
                {
                    // 수강 정원이 남아 있는 경우, - 1씩 감소하여 저장
                    classRepository.save(yogaClass);
                }
            }
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveCanceled_ChangeRemaining(@Payload ReserveCanceled reserveCanceled){

        if(reserveCanceled.isMe()){
            System.out.println("##### listener ChangeRemaining : " + reserveCanceled.toJson());

            // 예약이 취소되면, 총 수강정원을 +1씩 증가 한다.
            List<Class> yogaClassList = classRepository.findByClassId(reserveCanceled.getClassId());

            for(Class yogaClass : yogaClassList)
            {
                yogaClass.setClassStock(yogaClass.getClassStock() + 1);
                yogaClass.setReserveId(reserveCanceled.getReserveId());
                classRepository.save(yogaClass);
            }
        }
    }
}
