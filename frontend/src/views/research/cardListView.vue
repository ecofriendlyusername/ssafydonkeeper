<template>
  <div style="margin-top:55px;">
    <h1>소비 맞춤 카드 추천</h1>

    <div v-for="recom_card in recom_cards" :key="recom_card.id" class="cell"
      v-on:click="this.$router.push('/research/card/' + recom_card.id)"
      style="background-color:#E7E7E7; border-color:#E7E7E7 ;">
      <div style="display: flex; justify-content: space-between; background-color:white; padding: 10px; margin: 5px;">
        <div>
          <img :src="recom_card.imgPath" :alt=recom_card.imgPath @load="onImageLoad" class="cardImg">
        </div>
        <div>
          <div
            style="display:flex; font-weight: bold; overflow:hidden; text-overflow: ellipsis; white-space: nowrap; max-width: 100px;">
            {{ recom_card.name }}
          </div>
          <div style="display:flex">
            {{ recom_card.company }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RecomView',
  data() {
    return {
      recom_cards: [],
    }
  },
  methods: {
    getData() {
      this.axios.get(process.env.VUE_APP_API_URL + `/card/cards`)
        .then(res => {
          this.recom_cards = res.data
          console.log(this.recom_cards)
        })
    },
    onImageLoad(event) {
      if (event.target.width < event.target.height) {
        event.target.setAttribute("class", "trun");
      }
    },
  },
  mounted() {
    this.getData()
  }

}
</script>

<style>
.cardImg {
  width: 150px;
}

.trun {
  transform: rotate(90deg);
  height: 150px
}
</style>