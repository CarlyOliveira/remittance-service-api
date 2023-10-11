#!/bin/bash

create_tables()
{
  for SCHEMA_FILE in "${SCHEMAS_FILES[@]}"
  do
    echo -e "Criando tabela do arquivo $SCHEMA_FILE..."
    aws dynamodb create-table --cli-input-json file://"$SCHEMA_FILE" --endpoint-url http://localhost:4566 >> /dev/null
  done
}

load_data()
{
  for DATA_FILE in "${DATA_FILES[@]}"
  do
    echo -e "Inserindo dados do arquivo $DATA_FILE..."
    aws dynamodb put-item --cli-input-json file://"$DATA_FILE" --endpoint-url http://localhost:4566 >> /dev/null
  done
}



echo -e ""
echo -e "******************************************"
echo -e "* Iniciando criacao de recursos DynamoDB *"
echo -e "******************************************"

BASE_PATH=/docker-entrypoint-initaws.d
SCHEMAS_FILES=("$BASE_PATH"/dynamodb/schemas/*.json)
DATA_FILES=("$BASE_PATH"/dynamodb/data/*.json)

if [ -e "${SCHEMAS_FILES[0]}" ]; then
  echo -e ""
  echo -e "Iniciando criacao de tabelas DynamoDB..."
  create_tables
else
  echo -e ""
  echo -e "Scripts de criacao de tabelas DynamoDB nao encontrados..."
fi

if [ -e "${DATA_FILES[0]}" ]; then
  echo -e ""
  echo -e "Iniciando insercao de dados..."
  load_data
else
  echo -e ""
  echo -e "Scripts de insercao de dados nao encontrados..."
fi

echo -e "Configurando o npm"
npm config set registry http://registry.npmjs.org/
npm config set strict-ssl false

echo -e "A instalar plugin dynamodb admin "
npm install -g dynamodb-admin

echo -e ""
echo -e "*******************************************************"
echo -e "* Processo de criacao de recursos DynamoDB finalizado *"
echo -e "*******************************************************"
