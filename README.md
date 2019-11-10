# jetpackApp

参考
https://blog.csdn.net/feather_wch/article/details/88648559#LiveData_4

## LiveData核心关注点

### LiveData简介

1. LiveData是一种类，持有可被观察的数据。
1. LiveData是一种可感知生命周期的组件，意味着该组件重视其他app组件的生命周期，如Activity、Fragment、Service
该组件能确保，仅仅在Activity\Fragment\Service等组件都处于活跃的生命周期状态的时候，才去更新app组件。
