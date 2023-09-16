

# Controller -->> Spring MVC mimari için geliştirilmiş bir anotasyondur.
    Bunun REST mimari için dönüştürülmüş hali @RestController anotasyonudur.


## Javada StereoType denilen bazı anotasyonlar vardır..(@Component, @Service, @Repository, @Controller)
## @Component --> @Service, @Repository, @Controller
    @SpringBootApplication anotasyonu paketleri kontrol eder ve StreoType anotasyonları bulunan sınıfları arka tarafta hazırlar.
    Bu sınıflardan bir Spring Bean nesnesi üretir. bean(adı = aurhService) -->> (AuthService authService = new AuthService(); )
    Bu ürettiği nesne singleton yapıdadır. Singleton yapı -->> Bir kere üret, devamlı kullan..
    Nesne yönelimli programlamada IoC(Inversion of Control)  denilen bir "prensip" vardır.

    IoC -->> Bağımlılıkların yönetilmesi prensibidir. Bunu yaparken de Dependency Injection(DI) kullanımını tavsiye eder.
    IoC Container -->> Singleton üretilen nesnelerin depolandığı alandır.
        AuthService authService = new AuthService(); (singleton)
        UserService userService = new UserService(); (singleton)

    ApplicationContext = Spring Bean Container = Spring App Contex = IoC Container
    Üretilen beanler Spring tarafından application contex te tutulur ve injection yapılacağı zaman çağırılır ve kullanılır.
    Spring tarafından StereoType anotasyonları sayesinde oluşturulan bean'ler sınıf düzeyinde beanlerdir.
    ** Spring injection işlemi yapıldığında application context içerisinde önceden bean ismine bakarken artık
        oluşturulan bean' in "sınıf tipine" bakarak injection işlemini gerçekleştirir

## @Autowired nedir? Injection nedir? Aralarındaki fark nedir?
    Autowired -->> çalışma zamanında çalışırken, Constructor injection -->> derleme zamanı çalışır..
    -Autowired, metot her tetiklendiğinde metot içerisinde kullanılan bir injection nesnesi varsa gidip application contexten
    beani anlık olarak çeker ve initialize eder.
    -Injection ise program çalışırken application contexte gidip bean alıp initialize eder ve program çalıştığında
    bütün injectionlar beanler ile çalışabilir halde olur.
    -Autowired önerilmez. Temel sebebi de aralarındaki performans farkından dolayıdır. Birisi(Injection) bütün işlemleri yapıp
    sistemi kullanıma hazır hale getirirken, birisi(Autowired) ihtiyaç duyulduğunda her seferinde application contexte
    gidip ek bir işlem yapmaktadır.

## Register işlemi için bir token üretiniz ve bu token ile kullanıcnın durumunu ACTIVE yapınız.
    Daha sonra login olarak login token elde ediniz. 
    Opsiyonel olarak --> register da üretilen token 'ın önüne registerTOken: aıwhflahlfasfalk, login de üretilene
    loginToken: laskfhaowjfa   formatında bir çıktı da verebilirsiniz.


## ARAŞTIRMA KONULARI
    JDK vs JRE nedir?
    JVM nedir? Java JVM ile nasıl çalışır?
    Spring Stereo Type Annotation nedir? Anotasyonlar arasındaki farklar nelerdir?
    @Configuration ve @Bean nedir?
    @Configuration vs @Component
    ApplicationContext=SpringContainer nedir?
    Spring de bean mantığı nedir? (Spring bean)
    DI nedir? ne için kullanılır? tipleri nelerdir?
    Autowired nedir? Neden cons inj. neden autowired?
    SpringFramework vs SpringBoot?
    SOLID prensipleri nelerdir?
    RestAPI nedir? RESTful arch nedir?
    

