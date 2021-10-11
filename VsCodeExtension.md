# VsCode Extension 만들기

> * [VS Code Extension Generator 패키지 설치](#VS-Code-Extension-Generator-패키지-설치)
> * [확장 프로그램 프로젝트 생성](#확장-프로그램-프로젝트-생성)
> * [코드 분석](#코드-분석)



### VS Code Extension Generator 패키지 설치

우선 VS Code Extension을 만들기 위해서는 VsCode와 Node.js가 설치되어 있어야 한다.

그리고 VS Code Extension Generator 패키지를 설치해주어야 한다.

npm을 이용하여 설치하도록 하자 (기왕 하는거 global로 설치, mac에서는 -g 설치시에 sudo를 꼭 포함!)

```bash
$ npm install -g yo generator-code
```



### 확장 프로그램 프로젝트 생성

패키지 설치가 완료되면 프로젝트를 설치할 디렉토리로 이동하여 프로젝트 생성 명령어를 입력한다

```bash
$ yo code	
```

![](https://user-images.githubusercontent.com/41468004/131073512-fcdfba8a-4eb4-4daa-a6b8-4b0fe3be9433.png)



프로젝트 생성이 완료되면 code 명령어를 통해 해당 extension의 identifier를 입력하면 VSCode가 실행된다

프로젝트를 실행하여 디버그 모드를 통해 샘플 프로젝트가 동작하는지 확인해보자



![](https://user-images.githubusercontent.com/41468004/131074126-3a1bf23e-2e1d-4227-ae1b-73e5cee03993.png)

아주 잘된다!!



### 코드 분석

typescript는 처음이지만.. 나름 코드를 뜯어보자면



#### package.json

```js
{
	"name": "one-vscode-visualizer",
	"displayName": "OneVsCode",
	"description": "visualize circle-output json file",
	"version": "0.0.1",
	"engines": {
		"vscode": "^1.59.0"
	},
	"categories": [
		"Other"
	],
	"activationEvents": [
		"onCommand:one-vscode-visualizer.helloWorld"
	],
	"main": "./out/extension.js",
	"contributes": {
		"commands": [
			{
				"command": "one-vscode-visualizer.helloWorld",
				"title": "Hello World"
			}
		]
	},
	"scripts": {
		"vscode:prepublish": "npm run compile",
		"compile": "tsc -p ./",
		"watch": "tsc -watch -p ./",
		"pretest": "npm run compile && npm run lint",
		"lint": "eslint src --ext ts",
		"test": "node ./out/test/runTest.js"
	},
	"devDependencies": {
		"@types/vscode": "^1.59.0",
		"@types/glob": "^7.1.3",
		"@types/mocha": "^8.2.2",
		"@types/node": "14.x",
		"eslint": "^7.27.0",
		"@typescript-eslint/eslint-plugin": "^4.26.0",
		"@typescript-eslint/parser": "^4.26.0",
		"glob": "^7.1.7",
		"mocha": "^8.4.0",
		"typescript": "^4.3.2",
		"vscode-test": "^1.5.2"
	}
}

```

컨트리뷰트(contribute) 내의 커맨드들을 보면 아까 내가 입력해본 Hello World가 등록되어있는 것을 볼 수 있다

해당 Hello world를 입력하게 되면 activationEvents에 지정된 onCommand가 수행되는데 해당 명령어는



```typescript
import * as vscode from 'vscode';

export function activate(context: vscode.ExtensionContext) {

	let disposable = vscode.commands.registerCommand('one-vscode-visualizer.helloWorld', () => {
		vscode.window.showInformationMessage('Hello World from OneVsCode!');
	});

	context.subscriptions.push(disposable);
}

export function deactivate() {}

```

extensions.ts를 들어가보면 위에서 본 `one-vscode-visualizer.helloWorld` 가 하나의 함수로 해서 context에 등록되어진거같다.



여기까지가 코드를 분석해본 것이고! 

이제부터 json파일을 어떻게 입력받을지? => json 파일의 경로로? 아니면 뭐 다른 방법으로 => 그렇다면 이 과정에서 필요한건 typescript에서의 file import export? 이런게 필요할거같다

입력받은 파일을 분석해서 도식화하는 과정을 어떻게 할지... 그리고 보여지는 화면은 어떻게? 어떠한 언어로 설계할 수 있는지? => 이게 react를 연동해야하는지 뭔지.. 한번 공부해봐야할 것 같다

