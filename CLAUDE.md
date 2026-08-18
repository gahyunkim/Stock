# CLAUDE.md

## 커밋 메시지 규칙

- 커밋 메시지에 `Co-Authored-By: Claude Sonnet 5 <noreply@anthropic.com>` 트레일러를 추가하지 않는다.
- 커밋 메시지 제목 뒤에는 항상 관련 이슈 번호를 `(#번호)` 형태로 붙인다. (예: 브랜치명이 `feat-1-...`이면 `#1`, 명확하지 않으면 사용자에게 확인)

## Git / .gitignore 관리

- 빌드 산출물(`build/` 등)이나 로컬 IDE 설정 파일처럼 프로젝트에 불필요한 파일이 git에 staging되지 않도록 `.gitignore`를 항상 최신 상태로 유지한다.
- 작업 중 불필요한 파일이 untracked/staged로 잡히는 게 보이면, 먼저 사용자에게 물어보기보다 `.gitignore`를 고쳐서 근본적으로 해결한다.

## 아키텍처

- 클린 아키텍처(Clean Architecture)를 따른다.
- 구조나 컨벤션이 애매하거나 잘 모르겠으면 Google의 Now in Android(NIA) 샘플 프로젝트를 참고한다.

## UI / 코드 스타일

- UI는 Jetpack Compose를 사용한다.
- 코드 작성/수정 후에는 항상 ktlint로 검사한다.
