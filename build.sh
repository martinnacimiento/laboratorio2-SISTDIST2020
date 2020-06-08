set -e
echo "Compilando app..."
mvn compiler:compile -f "/workspaces/laboratorio-2/app/pom.xml"
echo "Creando JAR de app..."
mvn jar:jar -f "/workspaces/laboratorio-2/app/pom.xml"
echo "JAR de app creado!"
echo "Copiando dependecias de los proyectos..."
mvn dependency:copy-dependencies -f "/workspaces/laboratorio-2/app/pom.xml"
echo "Todo listo!"