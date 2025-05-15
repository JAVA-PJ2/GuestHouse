<p align="center">
  <img src="https://github.com/user-attachments/assets/f48d9533-25ac-4af4-8857-59773fc945fd" width="300"/>
</p>
<h2 align="center">🚩JAVA Project - 게스트하우스 예약 시스템</h2>


_자바 OOP 구조 설계와 Lambda 리팩토링까지 공부를 위한 **자바 프로젝트** (2025.05.07 ~ 2025.05.15)_

Jira, Github 협업, JAVA OOP와 CRUD, lambda 리팩토링 위주의 팀 프로젝트입니다.


## ✨ GH Service의 주요 기능
- CRUD
  
✅ 예약 기능 (CREATE)

✅ 숙소의 모든 예약 조회 기능 (READ)

✅ 해당 고객의 모든 예약 조회 기능 (READ)

✅ 예약 변경 기능 (UPDATE)

✅ 예약 취소 기능 (DELETE)

- 재사용 메소드
  
✅ 수용 가능 인원이 full인지 파악

✅ 예약 날짜 계산

- 추가 기능
  
✅ 가중치 기반 추천 숙소 리스트 반환 기능

✅ 우선순위 큐 예약 대기열 및 취소 시 자동 예약 기능

✅ 특정 날짜의 예약률 계산 기능


## 👤 GH Service의 UML
<img width="3328" alt="자바 프로젝트 - 2조 (8)" src="https://github.com/user-attachments/assets/954b9c25-bdfa-4732-a9b5-afcc06f18e6a" />

## 🎀 JIRA 협업 방식
![image](https://github.com/user-attachments/assets/3a6d6ae8-e1aa-4e83-991f-0e357fe56d1f)
- 대주제로 에픽을 파고 하위 항목으로 기능별 분리
- 담당자와 기한 지정
- 작업 완료 후 깃허브 이슈, PR 번호 기입과 작업 완료 코멘트 후 담당자 변경
- 할당된 담당자가 검토 후 완료 처리
![image](https://github.com/user-attachments/assets/69fd868f-14e9-4635-94a8-c13c19243a78)

## 🤝🏻 GITHUB 협업 방식
![image](https://github.com/user-attachments/assets/a4aa95ce-cee1-4d18-aa88-361d6a58f935)
- 이슈 : jira 이슈번호 ex) GHS-25 이슈명
- PR : jira 이슈번호 + 깃허브 이슈번호 ex) GHS-25#30
- 브랜치
  - main : 서비스 및 merge 브랜치
  - jira 이슈번호 + 깃허브 이슈번호 ex) GHS-25#30 : 작업 브랜치

## 📂 디렉토리 구조
```plaintext
📦 GuestHouse/
┣ 📂 src/
┃ ┣ 📂 com.gh.app
┃ ┃ ┗ 📄 Main.java
┃ ┣ 📂 com.gh.exception
┃ ┃ ┣ 📄 BookingCancelledException.java
┃ ┃ ┣ 📄 BookingNotFoundException.java
┃ ┃ ┣ 📄 InsufficientBalanceException.java
┃ ┃ ┗ 📄 NoSuchElementException.java
┃ ┣ 📂 com.gh.model
┃ ┃ ┣ 📄 Booking.java
┃ ┃ ┣ 📄 Guesthouse.java
┃ ┃ ┣ 📄 MusicGH.java
┃ ┃ ┣ 📄 PartyGH.java
┃ ┃ ┗ 📄 PetGH.java
┃ ┣ 📂 com.gh.service
┃ ┃ ┣ 📄 AnalyticsService.java
┃ ┃ ┣ 📄 BookingFileManager.java
┃ ┃ ┣ 📄 BookingService.java
┃ ┃ ┣ 📄 BookingServiceImpl.java
┃ ┃ ┣ 📄 GHService.java
┃ ┃ ┣ 📄 GuesthouseManager.java
┃ ┃ ┗ 📄 SearchService.java
┃ ┗ 📂 com.gh.user
┃ ┃ ┣ 📄 Account.java
┃ ┃ ┗ 📄 Customer.java
┣ 📄 README.md
```

## 👩‍💻 Contributor 👨‍💻

<table align="center">
  <tr>
    <td align="center" width="150px">
      <a href="https://github.com/seacrab808">
        <img src="https://avatars.githubusercontent.com/seacrab808" width="100px;" style="border-radius:50%;" alt="소유나"/>
        <br /><sub><b>소유나</b></sub>
      </a><br />
      <a href="https://github.com/seacrab808">GitHub</a>
    </td>
    <td align="center" width="150px">
      <a href="https://github.com/azure0929">
        <img src="https://avatars.githubusercontent.com/azure0929" width="100px;" style="border-radius:50%;" alt="양준용"/>
        <br /><sub><b>양준용</b></sub>
      </a><br />
      <a href="https://github.com/azure0929">GitHub</a>
    </td>
    <td align="center" width="150px">
      <a href="https://github.com/wooseunghwan">
        <img src="https://avatars.githubusercontent.com/wooseunghwan" width="100px;" style="border-radius:50%;" alt="우승환"/>
        <br /><sub><b>우승환</b></sub>
      </a><br />
      <a href="https://github.com/wooseunghwan">GitHub</a>
    </td>
  </tr>
</table>

