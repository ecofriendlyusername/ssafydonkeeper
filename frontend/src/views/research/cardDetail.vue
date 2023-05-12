<template>
    <div>
        <h1>{{ recom_card.name }}</h1>
        <h3>카드사: {{ recom_card.company }}</h3>
        <img :src="recom_card.imgPath" :alt="recom_card.name">
        <h3>전월실적: {{ recom_card.minimumSpending }} / 연회비: {{ recom_card.annualFee }}</h3>
        <ul>
            <li v-for="(benefit, idx) in recom_card.benefits?.split('\n')" :key="idx">
                {{ benefit }}
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    data() {
        return {
            recom_card: {}
        }
    },
    methods: {
        getData(){
            this.axios.get(process.env.VUE_APP_API_URL + `/card?id=${this.$route.params.id}`)
            .then((res) => {
                console.log(res.data);
                this.recom_card = res.data
            })
        }
    },
    mounted() {
        console.log(this.$route.params.id);
        this.getData()
    }
}
</script>

<style></style>