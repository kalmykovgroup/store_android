# Store Management App

## Описание

Приложение для добавления, редактирования и управления товарами, а также просмотра и изменения статусов заказов.

## Основные возможности

- **Товары**: просмотр списка товаров, добавление нового товара, редактирование и удаление существующих; загрузка изображений.
- **Заказы**: просмотр списка всех заказов, просмотр деталей (списка товаров), изменение статуса заказа.
- **Навигация**: быстрый доступ к разделам через нижнюю навигационную панель.

## Структура проекта

- **app**: модуль UI на Jetpack Compose, содержит `MainActivity`, компоненты экрана и навигацию.
- **data**: модуль получения и обработки данных, содержит:
  - `remote`: интерфейсы Retrofit и DTO
  - `repository`: бизнес-логика взаимодействия с API
- **shared**: общий модуль, содержит константы и DI-настройки.

## Архитектура

Используется MVVM с Hilt для внедрения зависимостей и Retrofit для сетевых запросов.

### ViewModel

- `ProductListViewModel`: управление состоянием списка товаров (CRUD операции).
- `OrderListViewModel`: управление заказами (загрузка списка, изменение статуса).

### Сервис API (`ApiService`)

```kotlin
interface ApiService {
    @GET("product/all")
    suspend fun getAllProducts(): ApiResponse<List<ProductDto>>

    @POST("product/create")
    suspend fun addProduct(@Body command: AddProductCommand): ApiResponse<ProductDto>

    @PUT("product/update")
    suspend fun updateProduct(@Body command: UpdateProductCommand): ApiResponse<ProductDto>

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") productId: UUID): ApiResponse<Boolean>

    @GET("orders/all")
    suspend fun getAllOrders(): ApiResponse<List<OrderDto>>

    @PUT("orders/{id}/status")
    suspend fun updateOrderStatus(
        @Path("id") id: UUID,
        @Body request: UpdateOrderStatusRequest
    ): ApiResponse<Boolean>

    @Multipart
    @POST("product/upload")
    suspend fun uploadProductImage(
        @Part image: MultipartBody.Part
    ): ApiResponse<ImageUploadResponse>
}
```

## Установка и запуск

1. Клонировать репозиторий.
2. Открыть в Android Studio Arctic Fox или новее.
3. Убедиться, что в `shared/constants/ApiConstants.kt` указан корректный `BASE_URL`.
4. Собрать проект через Gradle и запустить на устройстве или эмуляторе.

## Зависимости

- Kotlin 2.0.0
- Android Gradle Plugin 8.7.0
- Jetpack Compose (Compose BOM 2024.04.01)
- Hilt 2.48
- Retrofit 2.9.0 + OkHttp Logging 4.12.0
- Coil 2.5.0

## Лицензия

MIT © Калмыков Иван

