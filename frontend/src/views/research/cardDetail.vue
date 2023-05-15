<template>
    <div>
        <div style="width:250px; display: inline-block; margin-top: 20px;">
            <img :src="recom_card.imgPath" :alt="recom_card.name"
            style="width:100%">
        </div>
        <h2 style="margin-top:10px; margin-bottom: 0px;">{{ recom_card.name }}</h2>
        <p style="margin:0px;">{{ recom_card.company }}</p>
        
        <hr style="border: none; border-top: 3px dotted gray; color:black; background-color: #fff; height: 1px; width: 90%;">
        <h4 style="margin:0px;">전월실적: {{ recom_card.minimumSpending }} / 연회비: {{ recom_card.annualFee }}</h4>
        <hr style="border: none; border-top: 3px dotted gray; color:black; background-color: #fff; height: 1px; width: 90%;">
        
        <div style="color:orange; font-size:23px;">
            BENEFIT
        </div>
        <div style="font-weight:bold; font-size:17px;">
            주요혜택
        </div>

        <div v-for="(benefit, idx) in recom_card.benefits?.split('\n')" :key="idx">
            <div style="background-color:#F0F2F5; font-weight: bold; letter-spacing:3px; margin: 5px; padding: 5px; border-radius: 5px;">
                {{ benefit }}
            </div>
        </div>

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