<template>
  <div>
    <div>
      <h1>나의 최근 소비 패턴</h1>
    </div>

    <div style="display:flex; justify-content: space-between; align-items: center; background-color: #F7F7F7; padding: 20px 10px;">
      <div>
        <canvas id="chart" style="width:80%"></canvas>
      </div>
      <div style="font-size:17px;">
        <h3>가장 많이 사용한 곳</h3>
        <p v-for="(data, idx) in research_data" :key="idx">ㆍ{{ data.classification }} {{ data.percent }}%  </p>
      </div>
    </div>

    <div>
      <h2>카테고리별 사용 내역</h2>

      <div v-for="(data, idx) in spend_data" :key = idx>
        <div>
          {{ data.category }} : {{ data.amount }}
        </div>
      </div>

    </div>

    <div style="margin-top:55px;">
      <h1>소비 맞춤 카드 추천</h1>
      
      <div v-for="recom_card in recom_cards" :key="recom_card.id" class="cell" v-on:click="this.$router.push('/research/card/' + recom_card.id)" style="background-color:#E7E7E7; border-color:#E7E7E7 ;">
        <div style="display: flex; justify-content: space-between; background-color:white; padding: 10px; margin: 5px;">
          <div>
            <!-- <img :src="recom_card.imgPath" alt=recom_card.imgPath> -->
          </div>
          <div>
            <div style="display:flex; font-weight: bold;">
              {{ recom_card.name }}
            </div>
            <div style="display:flex">
              {{ recom_card.company }}
            </div>
          </div>
        </div>
      </div>
    
      <div style="display:flex; justify-content:end; padding:10px;" v-on:click="this.$router.push('/research/cardList')">더보기 > </div>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js/auto';

export default {
  data() {
    return {
      year:2023,
      month:5,
      research_data: [
        {
          classification: '여행',
          percent: 56
        },
        {
          classification: '외식',
          percent: 30
        },
        {
          classification: '쇼핑',
          percent: 15
        },
      ],
      spend_data:[],
      recom_cards: []
    }
  },
  methods:{
    getData() {
      this.axios.get(process.env.VUE_APP_API_URL + `/statistics/monthlyspendingbycat/${this.year}/${this.month}`)
      .then(res=>{
        console.log(`/statistics/monthlyspendingbycat/${this.year}/${this.month}`);
        this.spend_data = res.data
      })
      .then(() => {
        this.pieChartAdd()
      })

      this.axios.get(process.env.VUE_APP_API_URL + `/card/cards`  )
      .then(res => {
        this.recom_cards = res.data
        console.log(this.recom_cards)
      })
    },
    pieChartAdd() {
      new Chart(
        document.getElementById('chart'),
        {
          type: 'pie',
          options: {
            plugins: {
              legend: {
                display: false
              }
            }
          },
          data: {
            labels: this.research_data.map(row => row.classification),
            datasets: [
              {
                data: this.research_data.map(row => row.percent)
              }
            ]
          }
        }
      );
    },
  },
  mounted() {
    this.getData()
  }

}
</script>

<style>
.cell {
  display: block;
  border: black solid 1px;
}
</style>