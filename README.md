### Rumikube 자동풀이 앱

#### Android

	- minSdkVersion: 26
	- targetSdkVersion: 29 이상

#### 사용언어

- Java

#### 기능1 : 카드추가, 초기화

- 공통영역 또는 자신영역에 카드를 추가할 수 있습니다.
- 우측상단 버튼을 누르면 카드가 초기화 됩니다.

#### 기능2 : 카드조합

- 우측 중앙 버튼을 누르면 현재카드들로 조합을 할 수 있는지 계산합니다.
- 판단이 완료되면 우측하단버튼 모양이 바뀝니다.
- 우측하단버튼을 누르면 결과를 볼 수 있습니다. (조합된 카드들 또는 조합할 수 없다면 불가능이라고 알려줍니다.)

#### 알고리즘 : 완전탐색을 기반으로 작동합니다. 카드가 많을 경우 시간이 오래 걸릴 수 있습니다.
#### 참고 : 알고리즘 최적화 예정입니다.
