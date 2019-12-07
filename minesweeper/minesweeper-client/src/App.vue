<template>
  <div id="app">
    <div id="menu">
      <img alt="Restart" src="./assets/restart.png" title="Restart Game">
      <img alt="Flag" src="./assets/flag.jpeg" title="Mark Flags" @click="activateFlag" :class="{flagSelected: flag}">
    </div>

    <div id="scoreboard">
      <h4>Remaining Mines</h4>
      <div class="mines">99</div>
    </div>

    <board> </board>
  </div>
</template>

<script>
import board from './components/Board.vue'
import {serverEventBus} from './main';

export default {
  name: 'app',
  data() {
    return {
      flag: false
    }
  },
  components: {
    board
  },
  methods: {
    activateFlag() {
      serverEventBus.$emit('activateFlag', !this.flag);
      this.flag = !this.flag;
    }
  }
}
</script>

<style>
.flagSelected {
  border-color: green !important;
  border-width: 3px !important;
}

#menu img {
  height: 50px;
  width: 30px;
  border-width: 1px;
  border-color: lightslategray;
  border-style: solid;
  box-sizing: border-box;
  margin-left: 3px;
}

#menu img:hover {
  border-width: 3px;
}

#scoreboard {
  margin-top: 20px;
}

#scoreboard h4 {
  margin-bottom: 0px;
}

.mines{
  color: darkred;
  font-weight: bold;
  font-size: 50px;
  margin-bottom: 50px;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
