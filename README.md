# KM Coupon API

## Spec
- Java 8
- Spring Boot 2.6.1
- MySQL 5.7.34
- docker

## Run Application
```shell
$ docker-compose up --build
```

## API
- coupon-login-api (8080)
  - `POST /api/v1/member/signup` (회원가입)
  - `POST /api/v1/auth` (로그인(인증))

- coupon-service-api (8081)
  - `GET /api/v1/coupon/usable` (유효 쿠폰 조회)
  - `POST /api/v1/coupon/use` (쿠폰 사용 처리)

### 조건
1. 쿠폰 목록 조회 API
- 상품 금액을 입력받아 사용 가능한 쿠폰 목록을 조회합니다.
- 쿠폰 만료일시가 가까운 순으로 조회되어야 합니다.
- 쿠폰 만료일시가 같은 경우 할인 금액이 큰 순으로 조회되어야 합니다.
- (추가사항) 인증된 사용자 ID를 받아 사용자에 해당하는 유효한 쿠폰 조회합니다.

2. 쿠폰 사용 처리 API 
- 쿠폰 id와 상품 금액을 입력받고 쿠폰을 사용처리 합니다. (status를 USED로 업데이트)
- 해당 쿠폰이 사용 가능한 쿠폰이 아닌 경우 오류를 응답합니다.
  - 쿠폰사용가능 시작날짜 ~ 만료날짜에 해당하지 않는 경우
  - 이미 쿠폰 상태가 `USED` 인 경우
  - 최소 사용 가능 금액(`useMinAmount`)보다 구매하려는 상품 금액이 작은 경우
- 사용 처리의 응답은 상품 금액, 결제 금액(상품 금액에서 할인 금액을 차감하고 남은 금액), 실제 할인 금액(쿠폰의 할인금액 중 사용된 금액)을 포함해야 합니다.
- (인증된 사용자 ID와 쿠폰 ID를 기준으로 쿠폰이 조회 결과가 없는 경우도 예외 처리)

## 테스트 데이터
- 지정된 테스트 계정으로 로그인(`/api/v1/auth`)부터 진행하셔야 합니다.
```
- test1@example.com
- test2@example.com
- test3@example.com
- test4@example.com
```
- 비밀번호는 `test123!@#` 로 모두 동일합니다.

## DDL
- [flyway/db-migration/table/](https://github.com/beaniejoy/km-coupon/tree/main/flyway/db-migration/table)

## 체크사항
- lombok, mapstruct 제거
  - 생성자, getter 등 직접 구성
- api request 형식 다시 생각해보기
  - swagger로 확인
- 범용적으로 사용 가능한 interface에 대한 생각
  - validator 결국엔 특정 request에 종속되는 문제
  - service 단에서 dto를 만들고 return 하는 방식은 특정 api에만 제한적으로 사용될 수 밖에 없음
    - entity 직접 반환 (혹은 entity 내용을 그대로 copy한 객체로 반환)