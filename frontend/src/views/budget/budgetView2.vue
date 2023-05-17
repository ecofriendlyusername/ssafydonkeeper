<template>
    <div>
        <h1>{{ this.year }}년 {{ this.month }}월 나의 예산</h1>
        <button @click="this.$router.push('/budget/set')">예산 설정하기</button> 
        
        <br> 
        <br>

        <h3 style="display:flex; margin-top:-10px; margin-left: 20px;">총 예산: {{ formatAmount(this.budgetResponse.total_amount) }} ₩</h3>
        
        <div class="baseBar">
            <div class="gaugeBar" :style="{ width: this.total_spend_percent + '%' }"></div>
        </div>

        <div id="budgetTitle" style="font-size: 17px;">
            <div> <span style="font-weight:bold;">사용 예산:</span> {{ formatAmount(this.total_spend_amount) }} ₩</div>
        </div>

        <div id="budgetTitle" style="font-size: 17px;">
            <div> <span style="font-weight:bold;">남은 예산:</span> {{ formatAmount(this.budgetResponse.total_amount - this.total_spend_amount > 0 ? this.budgetResponse.total_amount - this.total_spend_amount : 0) }} ₩</div>
        </div>

        <div id="budgetTitle" style="font-size: 17px;">
            <div> <span style="font-weight:bold;">하루 권장 지출액:</span> {{ this.suggestedDaySpending }} ₩</div>
        </div>

        <div v-if="this.hasClassificationBudget" style="margin-top:45px;">
            <h3 id="budgetTitle">카테고리별 예산</h3>
            <div v-for="(item, idx) in spendingClassificationBudget" :key="idx" class="cell">
                <div>
                    <div id="budgetTitle" style="display:flex; align-items:center; justify-content: space-between;">
                        <div style="display:flex; align-items:center;">
                            <div id="budgetImg"></div>
                            <div style="font-weight: bold; margin-left: 10px; font-size: 16px;">{{ item.name }}</div>
                        </div>
                        <div> {{ formatAmount(getClassificationSpend(item.classificationId)) }}원 사용 </div>
                        <div style="color:#808080;">{{ formatAmount(getSpendingPercent(item.classificationId, item.amount)) }}%</div>
                        <div style="font-weight: bold;">{{ formatAmount(getRemainingAmount(item.classificationId, item.amount)) }}원 남음 </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            year: new Date().getFullYear(),
            month: new Date().getMonth() + 1,
            budgetResponse : [],
            spend_data : [],
            spendingClassificationBudget : [],
            total_spend_amount: 0,
            total_spend_percent : 0,
            suggestedDaySpending : 0,
            hasClassificationBudget : false,
        }
    },
    methods : {
        getClassificationSpend(id) {
            const spendData = this.spend_data.find(item => item.categoryId === id);
            if (spendData) {
                return spendData.amount;
            }
            return 0;
        },
        getRemainingAmount(id, amount) {
            const spendData = this.spend_data.find(item => item.categoryId === id);
            if (spendData) {
                return amount - spendData.amount;
            }
            return amount;
        },
        getSpendingPercent(id, amount) {
            const spendData = this.spend_data.find(item => item.categoryId === id);
            if (spendData) {
                return (spendData.amount / amount) * 100;
            }
            return 0;
        },
        formatAmount(amount) {
            if (amount) {
                return amount.toLocaleString();
            }
            return 0;
        }   

    },
    watch: {
        total_spend_amount () {
            if (this.budgetResponse.total_amount - this.total_spend_amount > 0) {
                const currentDate = new Date();
                const currentMonth = currentDate.getMonth();
                const currentYear = currentDate.getFullYear();
                const lastDayOfMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
                const remainingDays = lastDayOfMonth - currentDate.getDate() + 1;

                this.suggestedDaySpending = this.formatAmount(Math.floor(this.budgetResponse.total_amount / remainingDays));
            } else {
                this.suggestedDaySpending = 0;
            }
            
        },

    },
    mounted() {
        this.axios.get(process.env.VUE_APP_API_URL + `/account-book/budget/all/${this.year}/${this.month}`)
        .then(res => {
            this.budgetResponse = res.data;
            this.spendingClassificationBudget = res.data.datas;

            if (res.data.datas.length > 0) {
                this.hasClassificationBudget = true;
            }
            console.log(res.data);
        })

        this.axios.get(process.env.VUE_APP_API_URL + `/statistics/monthlyspendingbycat/${this.year}/${this.month}`)
        .then(res => {
            console.log(res.data);
            let sumAmount = 0;

            res.data.forEach(element => {
                console.log(element);
                if (element.amount > 0) {
                    this.spend_data.push(element);
                    sumAmount += element.amount;
                }
            });

            this.total_spend_amount = sumAmount;
            this.total_spend_percent = (sumAmount / this.budgetResponse.total_amount) * 100;
        })

    }

}
</script>

<style>
/* .baseBar {
  width: 300px;
  height: 28px;
  background-color: #E5E5E5;
  margin: 0 auto;
  padding: 2px;
  border-radius: 25px;
}

.gaugeBar {
  height: 28px;
  background-color: #4D82E6;
  border-radius: 25px;
} */

.baseBar {
  width: 300px;
  height: 28px;
  background-color: #E5E5E5;
  margin: 0 auto;
  padding: 2px;
  border-radius: 25px;
  overflow: hidden; /* overflow 속성 추가 */
}

.gaugeBar {
  height: 100%; /* 높이 100%로 변경 */
  background-color: #4D82E6;
  border-radius: 25px;
  transition: width 0.3s ease; /* 애니메이션 효과 추가 */
}

.cell {
  display: contents;
  align-items: center;
}

#budgetTitle {
  display: flex;
  margin-left: 20px;
  margin-top: 10px;
}

#budgetImg {
  background-color: #DEF0FF;
  width: 35px;
  height: 35px;
  border-radius: 20px;
}
</style>