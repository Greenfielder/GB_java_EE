<link rel='stylesheet' href='../style.css'>

<H1>Random comixbook shop</H1>
<ul>
    <c:url value="/" var="mainUrl">
    </c:url>
    <li><a href="${mainUrl}">Main</a></li>

    <c:url value="/catalog" var="catalogUrl">
    </c:url>
    <li><a href="${catalogUrl}">Catalog</a></li>

    <c:url value="/product" var="productUrl">
    </c:url>
    <li><a href="${productUrl}">Product</a></li>

    <c:url value="/cart" var="cartUrl">
    </c:url>
    <li><a href="${cartUrl}">Cart</a></li>

    <c:url value="/order" var="orderUrl">
    </c:url>
    <li><a href="${orderUrl}">Order</a></li>
    </ul>