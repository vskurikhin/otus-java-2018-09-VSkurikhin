<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang=en>
<head>
    <meta charset=UTF-8>
    <title>Home Work 3 of JavaEE 2018-09 | a work showcasing the feature of HTML5 &amp; CSS3</title>
    <link rel="stylesheet" href="css/style-all.min.css"/>
    <script type="text/javascript" language="javascript" src="js/script-all.min.js"></script>
</head>

<body class="body">
    <header class="w3-container main-header w3-center">
        <div class="div-header" style="width: 79%; min-width: 800px">
            <h1>Рога и копыта</h1>
            <h2>Заведение, занимается заготовкой «когтей и хвостов» и «горчицы и щёлока»</h2>
        </div>
        <div class="w3-cell w3-margin-0" style="width: 21%; min-width: 212px">
            <form class="find">
                <input class="find" name="search" id="search" placeholder="Искать здесь..." type="search">
                <button class="find" onclick="goSearch()"></button>
            </form>
        </div>
    </header>

    <nav>
        <ul>
            <li><a href="index.jsp">Главная</a></li>
            <li><a href="login.jsp">Вход в систему</a></li>
            <li><a href="contacts.jsp">Контакты</a></li>
            <li><a href="backform.jsp">Обратная связь</a></li>
            <li><a href="archive.jsp">Архивы новостей</a></li>
        </ul>
    </nav>

    <main class = "w3-row w3-gray w3-margin-0">
        <section class = "w3-col m9 w3-white w3-margin-0" style="width: 85%">
            <hr class="separator">
            <article class="article" id="article1">
                <header class="w3-row article">
                    <h1 class="w3-col m9 w3-margin-0 article">
                        <a class="article text-margin" href="#" title="Ссылка на новость" rel="bookmark">
                            Остап Бендер
                        </a>
                    </h1>
                </header>
            </article>

            <hr class="separator">
            <article class="article" id="article2">
                <header class="w3-row article">
                    <h1 class="w3-col m9 w3-margin-0 article">
                        <a class="article text-margin" href="#" title="Ссылка на новость" rel="bookmark">
                            Михаил Самуэлевич Паниковский
                        </a>
                    </h1>
                </header>
            </article>

            <hr class="separator">
            <article class="article" id="article3">
                <header class="w3-row article">
                    <h1 class="w3-col m9 w3-margin-0 article">
                        <a class="article text-margin" href="#" title="Ссылка на новость" rel="bookmark">
                            Шура Балаганов
                        </a>
                    </h1>
                </header>
            </article>

            <hr class="separator">
            <article class="article" id="article4">
                <header class="w3-row article">
                    <h1 class="w3-col m9 w3-margin-0 article">
                        <a class="article text-margin" href="#" title="Ссылка на новость" rel="bookmark">
                            Адам Козлевич
                        </a>
                    </h1>
                </header>
            </article>

            <hr class="separator">
            <article class="article" id="article5">
                <header class="w3-row article">
                    <h1 class="w3-col m9 w3-margin-0 article">
                        <a class="article text-margin" href="#" title="Ссылка на новость" rel="bookmark">
                            Александр Иванович Корейко
                        </a>
                    </h1>
                </header>
            </article>

            <hr class="separator">
        </section>

        <aside class = "w3-col m1 w3-margin-0" style="width: 15%">
            <ul>
                <li><a href="index.jsp">«ГЕРКУЛЕС»</a></li>
                <li><a href="login.jsp">«Лицом к деревне»</a></li>
                <li><a href="contacts.jsp">«Адлер»</a></li>
                <li><a href="archive.jsp">«Перерыв на обед»</a></li>
            </ul>
            <ul class="w3-margin">
                <li id="AMD"></li>
                <li id="GBP"></li>
                <li id="USD"></li>
            </ul>
            <script>getCBRJSON(fillUl);</script>
        </aside>
    </main>

    <footer class="w3-container w3-light-gray">
        <p class="w3-margin">&copy; 2018 ООО Рога и копыта. Свои права мы держим в надёжном месте.</p>
    </footer>
</body>
</html>