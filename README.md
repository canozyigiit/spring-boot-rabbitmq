## RabbitMq

Uygulamalarımız arasındaki haberleşme sync/async olmak üzere bir çok farklı şekilde olabilir. RabbitMQ bize bir çok farklı async Messaging Pattern’i kullanmamıza imkan sağlamaktadır. Kurduğunuz topolojiye göre aşağıdaki messaging patternleri kullanmanıza imkan sağlar;

#### Publish/Subscribe,
producer’dan fırlatılan mesaj, farklı kuyruklara gönderilip farklı işler gerçekleştiren consumer’lar tarafından alınıp işlenebilir. Örnek vermek gerekirse akıllı evinize siz yaklaştığınızda telefonunuzdan mesafenizi belirten bir mesaj fırlatalım ve bu mesaj farklı işler tarafından yakalanıp gerçekleştirilsin(klima çalışsın, panjurlar açılsın vs).
#### Message Qeueu, 
producer tarafından gönderilen mesajlar aynı kuyruk içerisine alınıp aynı işi gerçekleştiren consumer’lar tarafından işlenebilir.
#### Many to One, 
bir çok farklı producer tarafından gönderilen aynı tipteki mesajlar aynı kuyruğa eklenip consumer tarafından işlenebilir.
#### Request/Reply,
şuana kadar bahsettiklerim hep fire and forget prensibindeydi. Request/Reply ise producer tarafından gönderilen mesaj consumer tarafından işlendikten sonra response’u başka bir kuyruk üzerinden producer’a geri gönderir.
####
Kısacası RabbitMQ temel işlev olarak, kuyruk yapısı ve kendi bünyesinde bize sunduğu bir çok avantajı ile yukarıda bahsettiğim messing patternleri kullanarak servislerimiz/uygulamalarımız arasında haberleşmeyi sağlamaktadır.


## RabbitMq Yapısı
### Broker,
RabbitMQ’nun üzerinde çalıştığı sanal veya fiziksel makinedir. Brokerlar master/slave yapısında çalışabilmektedirler ve bunun için ayrı bir platforma/plugine ihtiyaç duymazlar.
### Producer,
Mesajları RabbitMQ’ya gönderen taraf olup, mesajla birlikte routing key bilgisi de gönderilmektedir. Tabi gönderilen mesaj network sorunlarından dolayı veya buna benzer başka bir sebepten dolayı broker’a iletilemeyebilir. Mesajın ulaşıp ulaşmadığıını “confirm.select” özelliği ile kontrol edebilirsiniz.
### Exchange,
Kendi içerisinde bir çok farklı kuyruğu barındıran ve gelen mesajları routing key sayesinde ilgili kuyruklara yönlendiren sanal bir alan diyebiliriz. 4 farklı exchange türü bulunup yukarıda bahsedilen messaging pattern’leri desteklemesini sağlayan ve RabbitMQ’nun en önemli farklarından birisidir.

### Message,
Burada farklı olarak mesajlara öncelik verebiliyor olmamızdır. Yani Kafkada mesajlar ilgili partitionlara gönderildiğinde sıralı olarak işleniyordu. RabbitMQ’da her kuyruk için mesajların alabileceği max priority (x-max-priority) belirtebiliyorsunuz. Böylelikle mesajlarda belirttiğiniz Priority değerine(Max değeri kuyrukta belirtilen kadar olabilir) göre kuyruk içerisinde mesajın sırası değiştirilip işlenme önceliği tanınabiliyor.
### Queue,
Aslında Kafkada yer alan partitionlara benziyor diyebiliriz. Görevi mesajları kendi üzerinde tutup consumer’lara mesajları eşit bir şekilde dağıtmaktır. Kuyrukların yukarıda bahsettiğimiz max priority gibi bir çok özelliği bulunmaktadır.

### Consumer,
kuyruklara subscribe olup, ilgili kuyruklardan mesajları alıp işleyen taraftır. Mesajı alıp işledikten sonra RabbitMQ’ya ack bilgisini dönmektedir, ack bilgisi alındıktan sonra mesaj kuyruktan çıkarılır. Consumer’lar scale edilmek istenildiği zaman herhangi bir değişiklik yapılmasına gerek yoktur. Yani yeni ayağa kaldırdığınız consumerlarda veya RabbitMQ üzerindeki konfigürasyonlarda değişikliğe gerek duymadan kuyruklara bağlanıp mesajları işlemeye başlayabilirler(Kafkada consumer-partition dengesi için değişiklikler yapmamız gerekebiliyordu).