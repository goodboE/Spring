## 소개
* 모임에 참가한 사람들의 정산을 도와주는 어플리케이션 (API)
* 소요시간 : 일주일
<br>
<br>  
<br>


## 기술
* Java 17
* Spring Boot 3.XX
* Spring Data JPA
* H2

<br>
<br>  
<br>

## 요구사항
### 회원가입
* 유저의 유니크 키(PK), 유저 아이디/비밀번호, 이름(닉네임)으로 구성합니다.

### 정산 (settlement)
* 한 유저당 정산을 여러 개 만들 수 있습니다.
* 유저는 여러 정산에 참가할 수 있습니다.
* 특정 정산에 참가한 유저들만 정산 내역(지출)들을 열람할 수 있습니다.

### 지출 (Expense)
* 정산은 여러개의 지출을 가지고 있습니다.
* 정산은 아래와 같은 지출을 가지고 있습니다.
* 기차표 예매 (지출 이름)
  * 유저1 (지출한 사람)
  * 80,000 원 (지출 금액)
  * 2023-08-01 (지출 날짜)
 
* 숙소 값
  * 유저2
  * 200,000 원
  * 2023-08-02
 
### 정산 결과 (balance)
* 정산 결과에서는 정산에 참여한 유저끼리 얼만큼 송금을 해줘야 하는지 보여줍니다.
* 예시)
  * 유저1 : 150,000 지출
  * 유저2 : 30,000 지출
  * 유저3 : 80,000 지출
  * 유저4 : 100,000 지출
  
-> 유저2 > 유저1 : 60,000 송금  
-> 유저3 > 유저4 : 10,000 송금  


<br>
<br>  
<br>

## ERD
![tricount](https://github.com/goodboE/Spring/assets/79093830/c6e7f4f3-6b72-4815-b527-901211c2e6fa)

<br>
<br>  
<br>
<br>
<br>  
<br>


## API
| Domain | Method | URI | Description |
| --- | --- | --- | --- |
| User | GET | /users | 전체 회원 목록을 가져온다. |
|  | POST | /signup | 회원가입 |
|  | POST | /login | 로그인 |
|  | POST | /logout | 로그아웃 |
|  |  |  |  |
| Settlement | GET | /settlements | 전체 정산 목록을 가져온다. |
|  | POST | /settlement/create | 정산 생성 |
|  | POST | /settlement/delete/{id} | 정산 삭제 |
|  | POST | /settlement/start/{id} | 정산 시작 후 관련 정보 삭제 |
|  |  |  |  |
| Expense | POST | /expense/create | 지출 생성 |
|  | GET | /expense/{settId} | 특정 정산에 대한 지출 내역을 가져온다.정산에 참여한 유저만 지출 내역을 가져올 수 있다. |

<br>
<br>  
<br>
<br>

<details>
<summary>테스트</summary>
<div markdown="1">

### 초기 데이터
![data](https://github.com/goodboE/Spring/assets/79093830/2684f5df-9577-4327-976f-6ce680e2e497)

### POST /settlement/start/1
* 1번 정산에 참여한 유저는 (유저1, 유저2, 유저3, 유저4)
* 1번 유저는 150,000 원
* 2번 유저는 30,000 원
* 3번 유저는 80,000 원
* 4번 유저는 100,000 원 지출하였으므로 2번 -> 1번 60,000원,  3번 -> 4번 10,000원 더 송금해야 한다.
```json
{
    "count": 2,
    "data": [
        {
            "senderUserNo": 2,
            "sendAmount": 60000,
            "receiverUserNo": 1
        },
        {
            "senderUserNo": 3,
            "sendAmount": 10000,
            "receiverUserNo": 4
        }
    ]
}
```
<br>
<br>

### POST /settlement/start/2
* 2번 정산에 참여한 유저는 (유저5, 유저6, 유저7)
* 5번 유저는 30,000 원
* 6번 유저는 10,000 원
* 7번 유저는 5,000 원 지출하였으므로 6번 -> 5번 5,000원,  7번 -> 5번 10,000원 더 송금해야 한다.
```json
{
    "count": 2,
    "data": [
        {
            "senderUserNo": 6,
            "sendAmount": 5000,
            "receiverUserNo": 5
        },
        {
            "senderUserNo": 7,
            "sendAmount": 10000,
            "receiverUserNo": 5
        }
    ]
}
```

</div>
</details>
