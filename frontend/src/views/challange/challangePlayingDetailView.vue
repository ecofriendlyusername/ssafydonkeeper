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
      this.axios({
        method: 'get',
        url: process.env.VUE_APP_API_URL + `/challenge/progress/${this.$route.params.id}`,
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
        url: process.env.VUE_APP_API_URL + `/challenge/join/${this.$route.params.id}`,
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