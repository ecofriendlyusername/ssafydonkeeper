<template>
    <div class="footer">
        <div style="display:flex; justify-content:space-between; padding: 10px;">
            <div v-for="(menu, idx) in menuBar" :key="idx">
                <router-link :to=menu.route>{{ menu.name }}</router-link>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'footerComponent',
    data() {
        return {
            menuBar: [
                {name:'가계부', route:'/book/calendar'}, 
                {name:'예산', route:'/budget'}, 
                {name:'소비패턴', route:'/research'}, 
                {name:'자산비교', route:'/comparison'}
            ]
        }
    }
}
</script>

<style>
.footer {
    width: 100%;
    max-width: 420px;
    position: sticky;
    bottom: 0;
    height: 80px;
    background-color: #ff9bc2;
    color: rgb(0, 0, 0);
    border-bottom: rgb(220, 220, 220) solid 0.1px;
}
</style>