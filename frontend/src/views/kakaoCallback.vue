<template>
    <div>
        {{ $route.query.code }}
    </div>
    
</template>

<script>
import axios from 'axios'
export default {
    name:'kakaoCallback',
    methods : {
        
    },
    mounted() {
        axios({
            method: 'post',
            url: `http://localhost:8080/api/auth/kakao/callback`,
            // headers: {
            //     'Authorization': `Bearer ${token}`,
            // },
            data: { // 인자로 보낼 데이터
                "code": this.$route.query.code
            }

          })
            .then((res) => {
              console.log(res.data)
            })

    }

}
</script>

<style>

</style>