# Stock Backend

Kotlin + Spring Boot 기반 SDUI 스키마 서버.

## 로컬 실행

```bash
./gradlew bootRun
```

기본 포트는 `8080`이며, `GET /screens/{screenId}`가 SDUI 화면 JSON을 내려준다 (예: `/screens/home`).

## Android 앱에서 연결하기

- 에뮬레이터: `http://10.0.2.2:8080/` — 에뮬레이터에서 호스트 머신의 `localhost`를 가리키는 전용 주소 (`core:network`의 `SduiApiFactory.EMULATOR_BASE_URL` 기본값)
- 실기기: 같은 네트워크에 있는 PC의 실제 IP로 접속 (`http://<PC IP>:8080/`), `SduiApiFactory.create(baseUrl = ...)`로 주소를 바꿔서 사용
