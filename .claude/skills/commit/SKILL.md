---
name: commit
description: 사용자가 Android Studio의 "Changes" 패널(또는 git add)로 선택(staged)해 둔 변경사항만을 기준으로 커밋 메시지를 자동 작성하고 커밋하는 스킬입니다. 사용자가 "/commit"을 입력하거나, "커밋해줘", "선택한 것만 커밋해줘", "커밋 메시지 만들어줘" 같은 말을 하면 이 스킬을 사용하세요. staged된 변경사항이 없으면 먼저 안내하고 멈춥니다. staged 파일만 대상으로 하며, 사용자가 선택하지 않은(unstaged/untracked) 파일은 절대 포함하지 않습니다.
---

# /commit — 선택한 변경사항 기반 커밋 스킬

이 스킬은 사용자가 Android Studio의 "Changes" 패널에서 체크한(=git에서 staged된) 파일들만을 대상으로, 저장소의 기존 커밋 스타일에 맞는 커밋 메시지를 만들어 커밋까지 수행한다.

**핵심 원칙: "선택한 내용"은 곧 git staged 상태다.** Android Studio Changes 패널에서 체크박스를 켠 파일은 `git add`로 staged된 파일과 동일하게 취급한다. 사용자가 선택하지 않은 파일(unstaged 수정, untracked 파일)은 절대로 임의로 `git add`하거나 커밋에 포함시키지 않는다.

## 전체 흐름

1. staged 상태 확인
2. staged 파일이 없으면 안내 후 중단
3. staged diff 분석 + 저장소 커밋 스타일 파악
4. 커밋 메시지 초안 작성
5. 민감 정보(시크릿) 포함 여부 점검
6. 커밋 실행
7. 결과 보고

## 1. staged 상태 확인

병렬로 아래를 실행한다:

* `git status --porcelain` — staged(`A `, `M `, `D ` 등 첫 컬럼에 변경 표시) / unstaged / untracked 파일을 구분한다.
* `git diff --cached` — staged된 내용의 실제 diff.
* `git log --oneline -10` — 최근 커밋 메시지 스타일 파악 (예: 이 저장소는 `chore:`, `build:` 같은 conventional commit 접두사 + 한글 설명 형식을 쓴다. 매번 확인해서 스타일이 바뀌었으면 그에 맞춘다).

## 2. staged 파일이 없는 경우

`git status --porcelain`에서 staged된 항목이 하나도 없다면:

* 커밋을 진행하지 않는다.
* unstaged/untracked 파일 목록을 보여주고, Android Studio Changes 패널에서 원하는 파일을 체크하거나 `git add <파일>`로 먼저 선택해달라고 안내한다.
* "전체를 다 staged 처리해서 진행할까요?" 같은 임의 제안은 하지 않는다 — "선택한 내용만"이라는 요청 취지에 어긋난다. 사용자가 명시적으로 "전체 다 올려서 커밋해줘"라고 하면 그때만 `git add -A` 대신 변경된 파일을 구체적으로 나열해서 add한다 (민감 파일 실수 포함 방지).

## 3. staged diff 분석 + 스타일 파악

* `git diff --cached`로 실제 변경 내용을 읽는다. 파일이 많으면 `git diff --cached --stat`으로 먼저 개요를 보고 필요한 부분만 상세히 본다.
* 변경의 성격을 분류한다: 새 기능(feat), 기존 기능 개선(update/enhance), 버그 수정(fix), 리팩터링(refactor), 빌드/설정(build/chore), 문서(docs) 등.
* `git log`에서 파악한 이 저장소의 커밋 메시지 컨벤션(접두사 형식, 언어, 길이)을 따른다.

## 4. 커밋 메시지 초안 작성

* "무엇을" 보다 "왜"에 초점을 맞춰 1~2문장으로 간결하게 작성한다.
* 저장소 컨벤션에 맞는 타입 접두사를 붙인다 (예: `feat:`, `fix:`, `chore:`, `build:`, `docs:`, `refactor:`).
* 시크릿이 의심되는 파일(`.env`, `*credentials*`, `*.key`, `local.properties`의 민감 값 등)이 staged에 포함되어 있으면 커밋 전에 반드시 사용자에게 알린다. 확실히 의심스러우면 커밋을 멈추고 확인부터 받는다.
* 초안을 사용자에게 보여준다. 이 스킬은 사용자가 "/commit"으로 명시적으로 커밋을 요청한 것이므로, 메시지 초안을 보여주면서 바로 커밋을 진행한다 (매번 별도 승인을 기다리지 않는다). 다만 메시지가 애매하거나 변경 성격이 여러 가지로 섞여 있어 판단이 어려우면 먼저 확인을 구한다.

## 5. 커밋 실행

* staged된 파일만 커밋한다 (`git add`를 추가로 호출하지 않는다 — 이미 staged된 것 외에는 건드리지 않는다).
* 커밋 메시지는 HEREDOC으로 전달해 포맷을 보존한다:

```bash
git commit -m "$(cat <<'EOF'
<타입>: <커밋 메시지>

Co-Authored-By: Claude Sonnet 5 <noreply@anthropic.com>
EOF
)"
```

* `--no-verify`, `--no-gpg-sign` 등 훅/서명 우회 플래그는 사용자가 명시적으로 요청하지 않는 한 절대 사용하지 않는다.
* pre-commit 훅이 실패하면 원인을 파악해서 고친 뒤, 기존 커밋을 `--amend` 하지 말고 새로 `git add` + `git commit`을 수행한다 (단, 이번에도 사용자가 원래 선택한 파일 범위를 벗어나지 않도록 주의한다).

## 6. 결과 보고

* `git status`로 커밋이 정상 반영됐는지 확인한다.
* 커밋 해시(짧은 형태), 커밋 메시지, 포함된 파일 목록을 정리해서 알려준다.
* push는 사용자가 명시적으로 요청하기 전까지 하지 않는다.

## 주의사항

* 절대 `git add -A`나 `git add .`로 전체를 한 번에 올리지 않는다 — 사용자가 선택하지 않은 파일이 섞여 들어갈 수 있다.
* staged된 파일이 없는데 임의로 뭔가를 골라 커밋하지 않는다.
* 커밋 메시지에 변경 이유가 불분명하면 파일 diff와 관련 코드 맥락을 좀 더 살펴보고 판단한다 — 추측성 메시지를 쓰지 않는다.
* `git reset --hard`, `git clean -f` 같은 파괴적 명령은 이 스킬 범위 밖이며 사용하지 않는다.
