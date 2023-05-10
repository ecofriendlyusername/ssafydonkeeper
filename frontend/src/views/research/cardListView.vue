<template>
  <div>
    <h1>소비 맞춤 카드 추천</h1>

    <ul>
      <li>카드1</li>
      <li>카드2</li>
      <li>카드3</li>
      <li>카드4</li>
    </ul>

  </div>
</template>

<script>
export default {
  name: 'RecomView',
  data() {
    return {
      cardData: [],
    }
  },
  methods: {
    getData(){
      this.axios.get(process.env.VUE_APP_API_URL + `/card/cards`)
      .then(res => {
        console.log(res.data);
      })
    }
  },
  mounted(){
    this.getData()
  }

}
</script>

<style>

</style>