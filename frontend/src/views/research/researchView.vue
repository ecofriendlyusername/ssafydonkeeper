<template>
  <div>
    <div>
      <h1>나의 최근 소비 패턴</h1>
    </div>

    <div
      style="display:flex; justify-content: space-between; align-items: center; background-color: #F7F7F7; padding: 20px 10px;">
      <div>
        <canvas id="chart" style="width:80%"></canvas>
      </div>
      <div style="font-size:17px;">
        <h3>가장 많이 사용한 곳</h3>
        <p v-for="(data, idx) in spend_data.filter((_, idx) => idx < key)" :key="idx">
          ㆍ{{ data.category }} {{ data.amount }} 원 </p>
      </div>
    </div>

    <div>
      <div style="margin-top: 10px; height:5px; width:100%; background-color:black"></div>
      <h2 style="margin-top:0px;">카테고리별 사용 내역</h2>
      <hr style="margin-top: -15px; height: 3px; color:#F0F2F5; width: 90%; border: none; background-color:#F0F2F5;">

      <div v-for="(data, idx) in spend_data.filter((el) => el.amount > 0)" :key=idx>
        <div style="display:flex; justify-content:space-between;">
          <div style="margin-left:30px;">
            {{ data.category }}
          </div>
          <div style="font-size:20px; margin-right:30px;">
            {{ data.amount }}원
          </div>
        </div>
      </div>
      <div style="margin-top: 10px; height:5px; width:100%; background-color:black"></div>
    </div>

    <div style="margin-top:-10px;">
      <h2>소비 맞춤 카드 추천</h2>

      <div style="background-color:#E7E7E7; padding: 1px; margin-top: -10px;">


        <div v-for="recom_card in recom_cards.filter((_, idx) => idx < 3)" :key="recom_card.id" class="cell"
          v-on:click="this.$router.push('/research/card/' + recom_card.id)"
          style="background-color:#E7E7E7; border-color:#E7E7E7 ;">
          <div style="display: flex; height: 100px; background-color:white; padding: 10px; margin: 5px;">
            <div class="cardImg">
              <img :src="recom_card.imgPath" :alt=recom_card.imgPath @load="onImageLoad" class="cardImg">
            </div>
            <div style="margin-left:15px;">
              <div
                style="display:flex; font-weight: bold; overflow:hidden; text-overflow: ellipsis; white-space: nowrap; max-width: 225px;">
                {{ recom_card.name }}
              </div>
              <div style="display:flex">
                {{ recom_card.company }}
              </div>
            </div>
          </div>
        </div>
      </div>


      <div style="display:flex; justify-content:end; padding:10px;" v-on:click="this.$router.push('/research/cardList')">
        더보기 > </div>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js/auto';

export default {
  data() {
    return {
      year: new Date().getFullYear(),
      month: new Date().getMonth(),
      spend_data: [],
      recom_cards: [],
      key: 4
    }
  },
  methods: {
    onImageLoad(event) {
      if (event.target.width < event.target.height) {
        event.target.setAttribute("class", "trun");
      }
    },
    getData() {
      this.axios.get(process.env.VUE_APP_API_URL + `/statistics/monthlyspendingbycat/${this.year}/${this.month}`)
        .then(res => {
          console.log(`/statistics/monthlyspendingbycat/${this.year}/${this.month}`);
          this.spend_data = res.data
        })
        .then(() => {
          this.axios.get(process.env.VUE_APP_API_URL + `/card/cards`)
            .then(res => {
              this.recom_cards = res.data
              console.log(this.recom_cards)
            })
        })
        .then(() => {
          this.pieChartAdd()
        })
    },
    pieChartAdd() {
      return new Promise((resolve) => {
        const temp = this.spend_data.filter((_, idx) => idx < this.key)
        console.log(temp);
        temp.push({
          'category': 'etc',
          'amount': this.spend_data.filter((_, idx) => idx > this.key).reduce((accumulator, currentValue) => accumulator + currentValue.amount, 0)
        })
        resolve(temp);
      })
        .then((temp) => {
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
                labels: temp.map(row => row.category),
                datasets: [
                  {
                    data: temp.map(row => row.amount)
                  }
                ]
              }
            }
          );
        })
        .catch((error) => {
          console.error("An error occurred:", error);
        });
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

.cardImg {
  width: 150px;
}

.trun {
  transform: rotate(90deg);
  height: 150px;
  margin-top: -25px;
}
</style>