<%--
  Created by IntelliJ IDEA.
  User: szymonciesla
  Date: 04/02/2022
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="layoutSidenav_nav">
    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
            <div class="nav">
                <div class="sb-sidenav-menu-heading"></div>
                <a class="nav-link" href="/dashboard">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Dashboard
                </a>
<%--                <div class="sb-sidenav-menu-heading"></div>--%>
                <a class="nav-link" href="/expenses/all">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    All expenses
                </a>
<%--                <div class="sb-sidenav-menu-heading"></div>--%>
                <a class="nav-link" href="/categories/all">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    All categories
                </a>
<%--                <div class="sb-sidenav-menu-heading"></div>--%>
                <a class="nav-link" href="/budgets/all">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    All budgets
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
                <a class="nav-link" href="/expenses/add">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Add expense
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
                <a class="nav-link" href="/categories/add">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Add category
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
                <a class="nav-link" href="/budgets/add">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Add budget
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
                <a class="nav-link" href="/stats/budgetStatsForm">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Budgets stats
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
                <a class="nav-link" href="/stats/categoriesStatsForm">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Categories stats
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
                <a class="nav-link" href="/account/add">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Add account
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
                <a class="nav-link" href="/income/add">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Add Income
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
                <a class="nav-link" href="/account/all">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    All accounts
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
                <a class="nav-link" href="/income/all">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    All incomes
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
                <a class="nav-link" href="/account/transfer">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Make transfer
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                     data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    </nav>
                </div>
            </div>
        </div>
    </nav>
</div>