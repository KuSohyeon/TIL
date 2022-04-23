# Vue.js 3.0에서 무엇이 달라졌는가? - Composition API 편

## 미리보기 [ Vue 2 vs Vue 3 코드 비교 ]

- Vue 2

```javascript
export default {
	name: 'foo',
	data() {
		return {
			foo: ''
		}
	}, 
	methods: {
		foo_method() {
			//...
		}
	}
} 
```

- Vue 3

```javascript
export default {
	name: 'foo',
	setup() {
		// 기존 data는 state로 작성
		const state = reactive({
			foo: ''
		})

		const foo_method = () = > {
			//...
		}

		// setup에서 반환된 것들은 다른 속성에서 사용 가능
		return { 
			state, // state.foo 처럼 접근 가능
			foo_method // 반환된 함수는 메소드와 동일하게 동작
		 }
	}
} 
```

# Composition API

![Optional API vs Composition API](./img/optional_vs_composition.png)
(이미지 출처 - [https://vuejs.org/guide/extras/composition-api-faq.html#more-flexible-code-organization](https://vuejs.org/guide/extras/composition-api-faq.html#more-flexible-code-organization))

## Vue 2 - Options based API

기존의 Vue 2 의 경우 prop, data, computed, hook 등의 옵션(또는 속성)의 규칙을 지켜서 작성한다. 이런 속성을 지켜 코드를 작성하다 보면 로직이 분리되게 된다. 이렇게 여러 개의 기능이 나누어져 코드 안에 뒤섞이게 되면 컴포넌트의 크기가 커질 수록 복잡성이 증가하게 된다.

Vue 3에서는 동일한 **논리적 관심사**를 관련있는 코드와 배치하기 위해 Composition API가 나오게 되었다.

## Vue 3 - Composition API

Composition API는 Vue 컴포넌트의 `setup()` 함수에 정의한다.

**`setup` 컴포넌트 옵션**

> 컴포넌트가 생성되기 전, `props`를 반환되면 실행되는 컴포넌트 옵션으로 composition API의 진입점 역할을 한다.
>

`setup` 컴포넌트의 실행 시점은 `beforeCreated`, `created`사이이며, (참고 - [vue3 라이프사이클 훅](https://v3.ko.vuejs.org/guide/composition-api-lifecycle-hooks.html))

`setup` 옵션 안에는 `this`가 존재하지 않는다. `setup`이 실행될 때, 컴포넌트의 인스턴스가 아직 생성되지 않기 때문이다. 즉, `props`를 제외하고는 컴포넌트의 다른 속성 내에 접근할 수 없다. (local state, computed, methods 등에 접근 X)

단, `setup`에서 반환된 모든 것은 컴포넌트의 템플릿 뿐만아니라 나머지 컴포넌트(computed, methos, 라이프사이클 훅)에 노출된다.

### 반응성 변수

Vue 3.0에서는 `ref`, `reactive` 를 사용해 어디서나 변수를 반응성 있도록 만들 수 있다.

**ref**

`ref`는 전달인자를 받고, 반응성 변수의 값에 접근하거나 변경할 수 있는 `value` 속성을 가진 객체를 반환한다.

```jsx
import { ref } from 'vue'

const counter = ref(0)

console.log(counter) // { value: 0 }
console.log(counter.value) // 0

counter.value++
console.log(counter.value) // 1
```

굳이 ‘ref로 값을 감싸야 할까?’ 라고 생각할 수 있지만, 값을 감싸는 래퍼 객체(wrapper object)를 가지고 있으면 어딘가에서 반응성을 잃어버릴 염려 없이 전체 앱에서 안전하게 전달할 수 있다.

(JavaScript에서는 `Number` 나 `String`과 같은 원시 타입(primitive types)은 참조에 의한 전달(pass by reference)이 아니라 값에 의한 전달(pass by value)이기 때문에 ref로 감싸면 pass by reference 처럼 사용이 가능)

**reactive**

`ref`와 `reactive`의 가장 큰 특징을 말하자면 `ref`는 값이 primitive일 때 사용하고, `reactive`는 object일 때 사용한다는 것이다.

```jsx
const name = ref('sohyun')
const age = ref(26)

const state = reactive({
	name: 'sohyun',
	age: 26
})
```

더 자세한 차이를 알고 싶다면... (참고 - [Vue 3 Composition API: ref() vs. reactive()](https://markus.oberlehner.net/blog/vue-3-composition-api-ref-vs-reactive/))

### `setup`  안에 라이프사이클 훅 등록

기존 라이프사이클 훅의 이름과 비슷하지만 접두사로 `on`이 붙는다.

```jsx
import { onBeforeMounted, onMounted, onBeforeUpdated, onUpdated } from 'vue'

export default {
	setup() {
		onBeforeMounted(() => {})
		onMountd(() => {})  
        onBeforeUpdated(() => {})
		onUpdated(() = > {})
		// ...
	}	
}
```

### `watch` 속성

vue에서 import한 `watch`로 기존과 동일한 작업 수행 가능

```jsx
import { ref, watch } from 'vue'

export default {
	setup() {
		const name = ref('sohyun')

		watch(name, (newValue, oldValue) => {
			console.log('새로운 name 값: ' + name.value)
		}
	}
}
```

### `computed` 속성

`computed`를 정의하고 싶은경우 import하여 사용할 수 있다.

```jsx
import { reactive, computed } from 'vue'

export default {
	setup() {
		const state = reactive({
			name: 'sohyun',
			uppercaseName: computed(() => state.name.toUpperCase())
		})

		return { state }
	} 
```

### 예제

그렇다면 이제 vue2 코드를 vue3으로 변경해보자

- 이 코드에서는 3개의 관심사(레포지토리 목록 보여줌, 검색, 필터링)를 가지고 있으며 각 관심사는 1,2,3으로 표현되어 있음
- vue 2 코드

```jsx
// src/components/UserRepositories.vue

export default {
  components: { RepositoriesFilters, RepositoriesSortBy, RepositoriesList },
  props: {
    user: { type: String }
  },
  data () {
    return {
      repositories: [], // 1
      filters: { ... }, // 3
      searchQuery: '' // 2
    }
  },
  computed: {
    filteredRepositories () { ... }, // 3
    repositoriesMatchingSearchQuery () { ... }, // 2
  },
  watch: {
    user: 'getUserRepositories' // 1
  },
  methods: {
    getUserRepositories () {
      // `this.user`를 사용해서 유저 레포지토리 가져오기
    }, // 2
    updateFilters () { ... }, // 3
  },
  mounted () {
    this.getUserRepositories() // 1
  }
}
```

- vue 3 코드

```jsx
import { fetchUserRepositories } from '@/api/repositories'
import { ref, onMounted, watch, toRefs, computed } from 'vue'

// 컴포넌트 내부
setup (props) {
  // `toRefs`를 사용하여 props의 `user`속성에 반응성 참조를 생성
  const { user } = toRefs(props)

  // 1. userRepositories 가져오는 부분
  const repositories = ref([])
  const getUserRepositories = async () => {
    // `props.user`에 참조 .value속성에 접근하여 `user.value`로 변경
    repositories.value = await fetchUserRepositories(user.value)
  }

  onMounted(getUserRepositories)

  // props로 받고 반응성참조가 된 user에 감시자를 세팅
  watch(user, getUserRepositories)

  // 2. 검색 기능
  const searchQuery = ref('')
  const repositoriesMatchingSearchQuery = computed(() => {
    return repositories.value.filter(
      repository => repository.name.includes(searchQuery.value)
    )
  })
    
  // 3. 필터 기능 (생략)
  // ...

  return {
    repositories,
    getUserRepositories,
    searchQuery,
    repositoriesMatchingSearchQuery
  }
}
```

어떻게 보면 ‘단순히 코드를 `setup`으로 옮기고 매우 크게 만든 것이 아닌가?’라고 생각할 수 있다.

이에 대한 답은 ‘YES’이다. 그렇기 때문에 위의 코드를 독립적인 기능을 하는 composition function으로 추출해야 한다.

1. 레포지토리 목록을 가져오기

```javascript
// src/composables/useUserRepositories.js

import { fetchUserRepositories } from '@/api/repositories'
import { ref, onMounted, watch, toRefs } from 'vue'

export default function useUserRepositories(user) {
  const repositories = ref([])
  const getUserRepositories = async () => {
    repositories.value = await fetchUserRepositories(user.value)
  }

  onMounted(getUserRepositories)
  watch(user, getUserRepositories)

  return {
    repositories,
    getUserRepositories
  }
}
```

1. 검색 기능

```javascript
// src/composables/useRepositoryNameSearch.js

import { ref, onMounted, watch, toRefs } from 'vue'

export default function useRepositoryNameSearch(repositories) {
  const searchQuery = ref('')
  const repositoriesMatchingSearchQuery = computed(() => {
    return repositories.value.filter(repository => {
      return repository.name.includes(searchQuery.value)
    })
  })

  return {
    searchQuery,
    repositoriesMatchingSearchQuery
  }
}
```

별도로 분리한 파일을 컴포넌트에서 사용하면 아래와 같다.

```javascript
// src/components/UserRepositories.vue
import { toRefs } from 'vue'
import useUserRepositories from '@/composables/useUserRepositories'
import useRepositoryNameSearch from '@/composables/useRepositoryNameSearch'
import useRepositoryFilters from '@/composables/useRepositoryFilters'

export default {
  components: { RepositoriesFilters, RepositoriesSortBy, RepositoriesList },
  props: {
    user: { type: String }
  },
  setup(props) {
    const { user } = toRefs(props)

    // 1. userRepositories 가져오는 부분
    const { repositories, getUserRepositories } = useUserRepositories(user)

    // 2. 검색 기능
    const {
      searchQuery,
      repositoriesMatchingSearchQuery
    } = useRepositoryNameSearch(repositories)

    // 3. 필터 기능
    const {
      filters,
      updateFilters,
      filteredRepositories
    } = useRepositoryFilters(repositoriesMatchingSearchQuery)

    return {
      // 필터링되지 않은 repositories는 실제로 신경쓰지 않기 때문에
      // `repositories`이름으로 필터링된 결과를 노출시킬 수 있습니다
      repositories: filteredRepositories,
      getUserRepositories,
      searchQuery,
      filters,
      updateFilters
    }
  }
}
```

지금까지 Composition API의 특징에 대해서 알아보았다.
Vue3으로 코드를 작성하는데 있어서 Composition API를 이해하는 것이 Vue3스러운 코드를 작성하는데 가장 도움이 되지 않을까 생각된다. 

# 참고

- [https://v3.ko.vuejs.org/ko-KR/guide/composition-api-introduction.html](https://v3.ko.vuejs.org/ko-KR/guide/composition-api-introduction.html)
- [https://v3.ko.vuejs.org/guide/composition-api-lifecycle-hooks.html](https://v3.ko.vuejs.org/guide/composition-api-lifecycle-hooks.html)
