<template>
  <div id="board">
    <div class="board-content"> 
        <cell v-for="c in 100" :key="c" :flag="flag"> </cell>
    </div>
  </div>
</template>

<script>
    import cell from './Cell.vue'
    import {serverEventBus} from '../main';

    export default {
      data() {
        return {
          flag: false
        }
      },
      created() {
        serverEventBus.$on('activateFlag', (flagState) => {
          this.flag = flagState;
        })
      },
      components: {
        cell
      }
    }
</script>

<style scoped>
.board-content {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  width: 300px;
  margin: auto;
}
</style>