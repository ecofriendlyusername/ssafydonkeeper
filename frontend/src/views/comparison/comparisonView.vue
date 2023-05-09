<template>
  <div>
    <h1>내 자산 비교</h1>
    <div style="display:flex; padding: 0px 10px; color: #5E29F6; font-weight: bold; font-size: 20px;">
      월 250~300만원 그룹<span style="color:black;">은</span>
    </div>
    <div style="display:flex; padding: 0px 10px; margin-bottom: 20px; font-weight: bold; font-size: 20px;">
      보통 이만큼 써요
    </div>

    <div class="barChart">
      <p style="margin-top:-15px; margin-bottom: 2px;">나</p>
      <div class="myBar">평균 {{ Math.round(groupData.total / 10000) }} 만원</div>
      <p style="margin-bottom: 2px;">250~300만원 그룹</p>
      <div class="yourBar">평균 {{ Math.round(groupData.groupAvg / 10000) }} 만원</div>
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
import axios from 'axios';

export default {
  data() {
    return {
      year: new Date().getFullYear(),
      month: new Date().getMonth(),
      groupData:{
        groupAvg: 1536415,
        total: 1248094
      },
      myUsed: {
        spend: 300,
        categories: [
        {
            classification: '예적금',
            percent: 67,
          },
          {
            classification: '보험',
            percent: 23,
          },
          {
            classification: '식비',
            percent: 10,
          }
        ]
      },
      yourUsed: {
        spend: 250,
        categories: [
          {
            classification: '식비',
            percent: 50,
          },
          {
            classification: '카페',
            percent: 35,
          },
          {
            classification: '의류',
            percent: 15,
          }
        ]
      },
    }
  },
  methods: {
    getData(){
      axios.get(process.env.VUE_APP_API_URL + `/statistics/compareusers/${this.year}/${this.month}?id=1148`)
      .then(res => {
        this.groupData = res.data;
      })
    },
    barChartSet(){
      if (this.groupData.total > this.groupData.groupAvg) {
        const your = document.querySelector('.yourBar');
        if (this.groupData.total < this.groupData.groupAvg/2) {
          your.style.width = '50%';
        } else {
          your.style.width = '80%';
        }
      } else {
        const my = document.querySelector('.myBar');
        if (this.groupData.groupAvg < this.groupData.total/2) {
          my.style.width = '50%';
        } else {
          my.style.width = '80%';
        }
      }
    },
    pieChartAdd(){
      new Chart(
        document.getElementById('myPieChart'),
        {
          type: 'pie',
          options: {
            // plugins: {
            //   legend: {
            //     display: false
            //   }
            // }
          },
          data: {
            labels: this.myUsed.categories.map(row => row.classification),
            datasets: [
              {
                data: this.myUsed.categories.map(row => row.percent)
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
            // plugins: {
            //   legend: {
            //     display: false
            //   }
            // }
          },
          data: {
            labels: this.yourUsed.categories.map(row => row.classification),
            datasets: [
              {
                data: this.yourUsed.categories.map(row => row.percent)
              }
            ]
          }
        }
      );
    }
  },
  async mounted() {
    await this.getData();
    await this.barChartSet();
    await this.pieChartAdd();

    
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