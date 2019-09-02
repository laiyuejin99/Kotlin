package coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    println("main current thread = "+ Thread.currentThread().id)


    runBlocking {
        println("blocking current thread = "+ Thread.currentThread().id)

        val job = launch {
            println("launch current thread = "+ Thread.currentThread().id)

            repeat(100) {
                println("挂起协程" + it)
                delay(500)
            }
        }

        //同步运行
        val job2 = async {
            println("async current thread = "+ Thread.currentThread())

            delay(500)
            println("job2 running")
            return@async "hello job2"
        }
        println("job2 = " + job2.await())

        delay(400)//主线程 暂停1300 毫秒
        println("main 线程等待中")
        job.cancel()
        job.join()
        println("主线程即将退出")


    }
}