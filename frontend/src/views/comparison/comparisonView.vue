<template>
  <div>
    <h1>내 자산 비교</h1>
    <div style="display:flex; padding: 0px 10px; color: #5E29F6; font-weight: bold; font-size: 20px;">
      월 {{ Math.round(data.base / 10000) }}~{{ Math.round(data.below / 10000) }}만원 그룹<span style="color:black;">은</span>
    </div>
    <div style="display:flex; padding: 0px 10px; margin-bottom: 20px; font-weight: bold; font-size: 20px;">
      보통 이만큼 써요
    </div>

    <div class="barChart">
      <p style="margin-top:-15px; margin-bottom: 2px;">나</p>
      <div class="myBar">평균 {{ Math.round(data.total / 10000) }} 만원</div>
      <p style="margin-bottom: 2px;">250~300만원 그룹</p>
      <div class="yourBar">평균 {{ Math.round(data.groupAvg / 10000) }} 만원</div>
    </div>

    <div style="background-color:#E5E5E5;">
      <p style="display:flex; padding:10px; font-weight:bold; font-size:20px;">여기에 가장 많은 소비를 해요</p>

      <div class="pieChart">
        <div style="width:50%">
          나
          <canvas id="myPieChart"></canvas>
        </div>
        <div style="width:50%">
          그룹
          <canvas id="yourPieChart"></canvas>
        </div>
      </div>
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
      data: {},
    }
  },
  methods: {
    getData() {
      this.axios.get(process.env.VUE_APP_API_URL + `/statistics/compareusers/${this.year}/${this.month}`)
        .then(res => {
          console.log(res.data);
          this.data = res.data;
        })
        .then(() => {
          this.barChartSet()
        })
        .then(() => {
          this.pieChartAdd()
        })
        .catch(err => { console.log(err); })
    },
    barChartSet() {
      if (this.data.total > this.data.groupAvg) {
        const your = document.querySelector('.yourBar');
        if (this.data.total > this.data.groupAvg / 2) {
          your.style.width = '40%';
        } else {
          your.style.width = '70%';
        }
      } else if (this.data.total < this.data.groupAvg){
        const my = document.querySelector('.myBar');
        if (this.data.groupAvg > this.data.total / 2) {
          my.style.width = '40%';
        } else {
          my.style.width = '70%';
        }
      } 
    },
    pieChartAdd() {
      new Chart(
        document.getElementById('myPieChart'),
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
            labels: this.data.user.map(row => row.category),
            datasets: [
              {
                data: this.data.user.map(row => row.amount)
              }
            ]
          }
        }
      );

      new Chart(
        document.getElementById('yourPieChart'),
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
            labels: this.data.group.map(row => row.category),
            datasets: [
              {
                data: this.data.group.map(row => row.amount)
              }
            ]
          }
        }
      );
    }
  },
  mounted() {
    this.getData();
  }
}
</script>

<style>
.pieChart {
  display: flex;
  padding-bottom: 20px;
}

.barChart {
  /* border: solid 1px black; */
  text-align: justify;
  padding: 20px;
  background-color: #E5E5E5;
}

.myBar {
  height: 20px;
  background-color: #5E29F6;
  color: white;
  text-align: right;
  padding: 5px;
}

.yourBar {
  height: 20px;
  background-color: #4D82E6;
  color: white;
  text-align: right;
  padding: 5px;
}
</style>