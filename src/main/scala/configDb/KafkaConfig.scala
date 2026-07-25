package configDb

case class KafkaConfig(
  bootstrapServers: String,
  topic: String,
  groupId: String
                      
                      )
