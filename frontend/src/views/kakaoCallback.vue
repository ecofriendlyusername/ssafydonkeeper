<template>
    <div>
        {{ $route.query.code }}
    </div>
</template>

<script>
import axios from 'axios'
axios.defaults.withCredentials = true; // 백엔드에서 cookie 세팅 할 수 있으려면 axios에 기본으로 되어있어야 한다. axios 한곳으로 몰아서 쓸거면 거기에 이거 작성해주세요.

export default {
    name: 'kakaoCallback',
    methods: {

    },
    mounted() {
        axios({
            method: 'post',
            url: `http://k8c209.p.ssafy.io:8080/api/auth/kakao/callback`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
            },
            data: { // 인자로 보낼 데이터
                "code": this.$route.query.code
            }
        })
        .then((res) => {
            console.log(res.data)
            this.$store.dispatch('setLoginCheck', true)
            this.$router.push('/book/calendar')
        })
        .catch(err => {
            this.$store.dispatch('setLoginCheck', true)
            this.$router.push('/book/calendar')
            console.log(err);
        })

    }

}
</script>

<style></style>
