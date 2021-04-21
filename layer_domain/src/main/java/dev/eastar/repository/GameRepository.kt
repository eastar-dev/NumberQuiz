package dev.eastar.repository

import dev.eastar.entity.GameMultiEntity
import dev.eastar.entity.GameSingleEntity

//아하 구조가 GameRepository 가 domain 으로 가야할 거 같아요
//GameRespository 의 return 이 Int 인데 만약 domainEntity 이면
//data 에서는 domainModel 을 받은다고 가정하면, repository layer 에서 domainModel를  domainEntity 로 mapping 해서 return 을 하고
//app 에서는 domainModel 이 아닌 domainEntity 만 사용할 수 있도록 설계할 수 있을거 같아요
//이렇게 하면 p domain 에 있는 모델만 쓰고 dresenter 여기서는 app 인데.ata 에 있는 모델을 사용하지 않는 구조로 만들수 있는데
//이렇게 하면 관심사 분리르 할 수 있어서 더 안전한 상태로 만들 수 있을거 같습니다.
interface GameRepository {
    fun getSingleGame(): GameSingleEntity
    fun getMultiGame(): GameMultiEntity
}
