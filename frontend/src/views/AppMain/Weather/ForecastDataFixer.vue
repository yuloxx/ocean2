<template>
    <span id = "DataFixer">{{ fixedValue }}</span>
</template>

<script>
export default{
    props:['floatValue'],
    computed:{
        fixedValue(){
            return this.floatValue.toFixed(2)
        }
    }
}
</script>