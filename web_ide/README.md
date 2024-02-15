## 소개
* [팀 프로젝트](https://github.com/KoDongDanGyeol/challenge-with-me-be) 에서 맡은 web_ide 파트를 따로 테스트하고자 만든 연습 프로젝트
* 필요한 API를 미리 개발해본다.
* 소요시간 : 일주일 (+ 지속적으로 업데이트)
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
* 클라이언트 에서 받은 code를 컴파일 후 실행시킬 수 있다.
* DB 안에 존재하는 테스트케이스와 결과값을 비교해 정답 여부를 알 수 있고 소요시간을 알 수 있다.
* 테스트케이스 추가를 위해 클라이언트에서 테스트케이스의 타입, 값, 기댓값을 조회할 수 있다.

<br>
<br>  
<br>

## API
| Domain | Method | URI | Description |
| --- | --- | --- | --- |
| IDE | POST | /{problemId} | 컴파일 후 실행, 공개 테스트케이스와 비교 |
|  | POST | /{problemId}/submit | 컴파일 후 실행, 모든 테스스케이스와 비교 |
|  | GET | /{problemId}/testcase | 공개 되어 있는 테스트케이스의 타입과 값을 가져온다. |


<br>
<br>  
<br>
<br>

<details>
<summary>테스트</summary>
<div markdown="1">

### 초기 데이터
![ide_db](https://github.com/goodboE/Spring/assets/79093830/55be70ac-4493-4a65-99cb-350b1cffaa80)


### POST /1
```json
{
    "code" : "public class Solution {\n    public int solution(int n) {\n        int answer = 0;\n\n        if (n == 0) {\n            return 0;\n        }\n\n        for(int i = 1; i <= n; i++) {\n            if (n % i == 0) {\n                answer += i;\n            }\n        }\n\n\n        return answer;\n    }\n}"
}
```

```json
{
    "submitType": "run",
    "runResult": [
        {
            "input": [
                "12"
            ],
            "expected": "int:28",
            "output": "테스트를 통과하였습니다.",
            "performance": 0,
            "errorMsg": null,
            "passed": true
        },
        {
            "input": [
                "5"
            ],
            "expected": "int:6",
            "output": "테스트를 통과하였습니다.",
            "performance": 1,
            "errorMsg": null,
            "passed": true
        },
        {
            "input": [
                "0"
            ],
            "expected": "int:0",
            "output": "테스트를 통과하였습니다.",
            "performance": 0,
            "errorMsg": null,
            "passed": true
        }
    ]
}
```
### POST /1/submit
```json
{
    "code" : "public class Solution {\n    public int solution(int n) {\n        int answer = 0;\n\n        if (n == 0) {\n            return 0;\n        }\n\n        for(int i = 1; i <= n; i++) {\n            if (n % i == 0) {\n                answer += i;\n            }\n        }\n\n\n        return answer;\n    }\n}"
}
```

```json
{
    "submitType": "submit",
    "runResult": [
        {
            "input": [
                "12"
            ],
            "expected": "int:28",
            "output": "테스트를 통과하였습니다.",
            "performance": 1,
            "errorMsg": null,
            "passed": true
        },
        {
            "input": [
                "5"
            ],
            "expected": "int:6",
            "output": "테스트를 통과하였습니다.",
            "performance": 0,
            "errorMsg": null,
            "passed": true
        },
        {
            "input": [
                "0"
            ],
            "expected": "int:0",
            "output": "테스트를 통과하였습니다.",
            "performance": 0,
            "errorMsg": null,
            "passed": true
        }
    ],
    "submitResult": [
        {
            "errorMsg": null,
            "accuracyTest": "통과 (수행시간 : 0)",
            "passed": true
        },
        {
            "errorMsg": null,
            "accuracyTest": "통과 (수행시간 : 0)",
            "passed": true
        },
        {
            "errorMsg": null,
            "accuracyTest": "통과 (수행시간 : 0)",
            "passed": true
        },
        {
            "errorMsg": null,
            "accuracyTest": "통과 (수행시간 : 0)",
            "passed": true
        }
    ]
}
```

### GET /3/testcase
```json
{
    "testcaseTypes": {
        "input": [
            "int[][]",
            "int",
            "int"
        ],
        "expected": "int"
    },
    "testcaseValues": [
        {
            "input": [
                "[[1,2],[2,3]]",
                "3",
                "2"
            ],
            "expected": "5"
        },
        {
            "input": [
                "[[4,4,3],[3,2,2],[2,1,0]]",
                "5",
                "3"
            ],
            "expected": "33"
        }
    ]
}
```
</div>
</details>
