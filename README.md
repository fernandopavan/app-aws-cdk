# app-aws-cdk

Criação de dois microservices em Java utilizando Spring Boot em containers Docker, construindo uma aplicação de backend para interagir com recursos da AWS, como os citados a seguir. Esses recursos serão criados na AWS utilizando o AWS Cloud Development Kit (CDK), uma forma moderna de modelamento e provisionamento de infraestrutura na AWS. O AWS CDK é um das melhores ferramentas de infraestrutura como código, ou IaC, para a AWS.

- Microsserviços com Spring Boot com o AWS ECS e o Fargate, o Serverless compute for containers da AWS, integrando com outros serviços como SNS, SQS e S3.
- Infraestrutura dos recursos na AWS com o AWS Cloud Development Kit (CDK), modelando e provisionando os recursos utilizando a linguagem Java.
- Persistencia de eventos utilizando tabela do AWS DynamoDB, configurando-a em modo provisionado com auto scaling e em modo on demand.
- Microsserviços Spring boot baseados em containers Docker que utiliza o AWS ECS.
- Persistencia de dados em uma instância do Postgres construída com o AWS RDS.
- Publicação de eventos em tópicos utilizando o AWS SNS.
- Consumo de eventos em filas utilizando o AWS SQS.
- Constrói um mecanismo de importação de arquivos utilizando o AWS S3.
- Configura opções de auto-scaling de tabelas do DynamoDB.
- Configura chaves compostas nas tabelas do DynamoDB.
- Criação um application load balancer para dividir o tráfego entre várias instâncias da aplicação.
- Possível monitorar serviços construídos com AWS ECS utilizando CloudWatch Insights.
- Possível monitorar serviços como SQS, ALB e DynamoDB através de seus gráficos e métricas.

Funcionamento das Apps:

App 01: 
	Cadastro de produtos:
		- Cadastra, atualiza, lista, busca por código e remove por id; (RDS)
		- Publica evento no SNS ao cadastrar, atualizar e remover produto. 
		- O evento enviado ao SNS, dispara notificação para o e-mail cadastrado(via config) e para o tópico 'product-events'.
		
	Invoices:
		- Gera uma URL pública para subir arquivos para o S3, lista invoices e busca por 'bycustomername'.
		- Ao receber um novo arquivo no bucket 'pcs-invoice', o S3 notifica o tópico 's3-invoice-events'(SNS) e depois o 's3-invoice-events'(SQS) consome a mensagem, manipula e insere na tabela de 'invoice' (RDS)

App 02: 
	- Consome e manipula as mensagens do tópico 'product-events', publicada pela App 01, e salva um evento na tabela 'product-events' no DynamoDB;
	- Possivel listar e filtrar os eventos salvos no DynamoDB;
