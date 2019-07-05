package com.jihazardrestapi.demorestapi.events;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
// 기본생성자가 아무것도 포함하지 않은 생성자를 모두 생성하기 위한 어노테이션
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
//@Data 어노테이션을 쓰지 않는 이유는 EqualsAndHash 코드를 모든 항목을 참조하여 만들기 때문에 스택오브플로우가 발생할 가능성이 있어서 쓰면안됨
public class Event {
    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location;
    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    @Enumerated(EnumType.STRING) //ORDINAL 은 ENUM의 순서대로 번호저장
    private EventStatus eventStatus = EventStatus.DRAFT;

    public void update() {
        if(this.basePrice == 0 && this.maxPrice == 0) this.free = true;
        else this.free = false;

        if(this.location == null || (this.location).trim().isEmpty()) this.offline = false;
        else this.offline = true;

    }
}
