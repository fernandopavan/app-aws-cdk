<h1 class="code-line" data-line-start=0 data-line-end=1 ><a id="appawscdk_0"></a>app-aws-cdk</h1>
<p class="has-line-data" data-line-start="2" data-line-end="3">Criação de dois microservices em Java utilizando Spring Boot em containers Docker, construindo uma aplicação de backend para interagir com recursos da AWS, como os citados a seguir. Esses recursos serão criados na AWS utilizando o AWS Cloud Development Kit (CDK), uma forma moderna de modelamento e provisionamento de infraestrutura na AWS. O AWS CDK é um das melhores ferramentas de infraestrutura como código, ou IaC, para a AWS.</p>
<ul>
<li class="has-line-data" data-line-start="4" data-line-end="5">Microsserviços com Spring Boot com o AWS ECS e o Fargate, o Serverless compute for containers da AWS, integrando com outros serviços como SNS, SQS e S3.</li>
<li class="has-line-data" data-line-start="5" data-line-end="6">Infraestrutura dos recursos na AWS com o AWS Cloud Development Kit (CDK), modelando e provisionando os recursos utilizando a linguagem Java.</li>
<li class="has-line-data" data-line-start="6" data-line-end="7">Persistencia de eventos utilizando tabela do AWS DynamoDB, configurando-a em modo provisionado com auto scaling e em modo on demand.</li>
<li class="has-line-data" data-line-start="7" data-line-end="8">Microsserviços Spring boot baseados em containers Docker que utiliza o AWS ECS.</li>
<li class="has-line-data" data-line-start="8" data-line-end="9">Persistencia de dados em uma instância do Postgres construída com o AWS RDS.</li>
<li class="has-line-data" data-line-start="9" data-line-end="10">Publicação de eventos em tópicos utilizando o AWS SNS.</li>
<li class="has-line-data" data-line-start="10" data-line-end="11">Consumo de eventos em filas utilizando o AWS SQS.</li>
<li class="has-line-data" data-line-start="11" data-line-end="12">Constrói um mecanismo de importação de arquivos utilizando o AWS S3.</li>
<li class="has-line-data" data-line-start="12" data-line-end="13">Configura opções de auto-scaling de tabelas do DynamoDB.</li>
<li class="has-line-data" data-line-start="13" data-line-end="14">Configura chaves compostas nas tabelas do DynamoDB.</li>
<li class="has-line-data" data-line-start="14" data-line-end="15">Criação um application load balancer para dividir o tráfego entre várias instâncias da aplicação.</li>
<li class="has-line-data" data-line-start="15" data-line-end="16">Possível monitorar serviços construídos com AWS ECS utilizando CloudWatch Insights.</li>
<li class="has-line-data" data-line-start="16" data-line-end="18">Possível monitorar serviços como SQS, ALB e DynamoDB através de seus gráficos e métricas.</li>
</ul>
<p class="has-line-data" data-line-start="18" data-line-end="19">Funcionamento das Apps:</p>
<p class="has-line-data" data-line-start="20" data-line-end="22">App 01:<br>
Cadastro de produtos:</p>
<ul>
<li class="has-line-data" data-line-start="22" data-line-end="23">Cadastra, atualiza, lista, busca por código e remove por id; (RDS)</li>
<li class="has-line-data" data-line-start="23" data-line-end="24">Publica evento no SNS ao cadastrar, atualizar e remover produto.</li>
<li class="has-line-data" data-line-start="24" data-line-end="26">O evento enviado ao SNS, dispara notificação para o e-mail cadastrado(via config) e para o tópico ‘product-events’.</li>
</ul>
<p class="has-line-data" data-line-start="26" data-line-end="27">Invoices:</p>
<ul>
<li class="has-line-data" data-line-start="27" data-line-end="28">Gera uma URL pública para subir arquivos para o S3, lista invoices e busca por ‘bycustomername’.</li>
<li class="has-line-data" data-line-start="28" data-line-end="30">Ao receber um novo arquivo no bucket ‘pcs-invoice’, o S3 notifica o tópico ‘s3-invoice-events’(SNS) e depois o ‘s3-invoice-events’(SQS) consome a mensagem, manipula e insere na tabela de ‘invoice’ (RDS)</li>
</ul>
<p class="has-line-data" data-line-start="30" data-line-end="31">App 02:</p>
<ul>
<li class="has-line-data" data-line-start="31" data-line-end="32">Consome e manipula as mensagens do tópico ‘product-events’, publicada pela App 01, e salva um evento na tabela ‘product-events’ no DynamoDB;</li>
<li class="has-line-data" data-line-start="32" data-line-end="34">Possivel listar e filtrar os eventos salvos no DynamoDB;</li>
</ul>
