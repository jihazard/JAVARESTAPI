package com.jihazardrestapi.demorestapi.account;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
// 기본생성자가 아무것도 포함하지 않은 생성자를 모두 생성하기 위한 어노테이션
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Account {
    @Id @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;
}
