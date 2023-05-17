<template>
  <div>
    <h3>{{ data.name }}</h3>
    <p>시작일: {{  }}</p>
    <p>마감일: {{  }}</p>
    <p>콘텐츠 내용: {{  }}</p>
    <p>참여자 수: {{  }}</p>
    <p v-if="data.participant">참여 불가능</p>
    <p v-else v-on:click="joinChallenge()">참여 가능</p>
  </div>
</template>

<script>
export default {
  data(){
    return {
      data: []
    }
  },
  methods:{
    getChallengeDetail() {
      //챌린지 시작전 챌린지 디테일
      //참여여부 true false로 줘가지고 그거로 참여버튼 활성화 비활성화 하면 됨.
      //이거는 안될수도 있음 무시하고 하셈 아까는 됨.
      this.axios({
        method: 'get',
        url: `http://localhost:8080/api/challenge/${this.$route.params.id}`,
      })
        .then((res) => {
          this.data = res.data
          console.log("getChallengeDetail")
          console.log(res.data)
        })
    },
    joinChallenge() {
      //챌린지 참여 잘됨.
      this.axios({
        method: 'get',
        url: `http://localhost:8080/api/challenge/join/${this.$route.params.id}`,
      })
        .then((res) => {
          console.log("joinChallenge")
          console.log(res.data)
        })

    },
  },
  mounted() {
    this.getChallengeDetail()
  }
}
</script>

<style>

</style>