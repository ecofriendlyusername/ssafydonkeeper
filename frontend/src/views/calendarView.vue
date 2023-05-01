<template>
  Calendar
</template>

<script>
export default {
  name: 'calendarView',
  computed: {
    title() {
      return this.$store.getters.getTitle;
    }
  },
  mounted() {
    this.$store.dispatch('setTitle', '가계부');
  }
}
</script>

<style></style>