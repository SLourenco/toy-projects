<template>
  <div class="cell" @click="revealCell">
    <img src="../assets/flag.jpeg" v-if="flagged">
  </div>
</template>

<script>
    export default {
        props: ['flag'],
        data() {
            return {
                flagged: false
            }
        },
        methods: {
            revealCell() {
                if(this.flag) {
                    this.flagged = !this.flagged;
                    return;
                }
                console.log('Call backend to get result of cell');
            }
        }
    }
</script>

<style scoped>
.cell {
    width: 30px;
    height: 30px;
    background-color: lightgrey;
    border-style: solid;
    border-color: black;
    border-width: 1px;
    box-sizing: border-box;
}

.cell:hover {
    border-width: 2px;
}

.cell img{
    width: 30px;
    height: 30px;
}
</style>