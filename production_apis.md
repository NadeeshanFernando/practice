# Java Collections Through Production-Style REST APIs

This README contains **15 production-style API ideas** that demonstrate how Java Collections are commonly used in real Spring Boot backend applications.

## Goal

These APIs are designed to help practice the most common real-world usage of:

- `List`
- `Set`
- `Map`
- `Queue`

instead of building demo-only endpoints like “show ArrayList” or “show HashSet”.

---

## 1. Get all users

**Endpoint**

```http
GET /api/users
```

**Purpose**

Return all users in the system.

**Main Collection Used**

- `List<UserDto>`

**Expected Response**

```json
{
  "count": 3,
  "data": [
    {
      "id": 1,
      "name": "Anton",
      "role": "ADMIN",
      "department": "IT"
    },
    {
      "id": 2,
      "name": "Nimal",
      "role": "USER",
      "department": "HR"
    },
    {
      "id": 3,
      "name": "Kamal",
      "role": "MANAGER",
      "department": "IT"
    }
  ]
}
```

---

## 2. Get unique user roles

**Endpoint**

```http
GET /api/users/roles
```

**Purpose**

Return unique roles only.

**Main Collection Used**

- `Set<String>`

**Expected Response**

```json
{
  "count": 3,
  "data": [
    "ADMIN",
    "MANAGER",
    "USER"
  ]
}
```

---

## 3. Group users by department

**Endpoint**

```http
GET /api/users/grouped-by-department
```

**Purpose**

Return users grouped by department.

**Main Collection Used**

- `Map<String, List<UserDto>>`

**Expected Response**

```json
{
  "data": {
    "IT": [
      {
        "id": 1,
        "name": "Anton",
        "role": "ADMIN"
      },
      {
        "id": 3,
        "name": "Kamal",
        "role": "MANAGER"
      }
    ],
    "HR": [
      {
        "id": 2,
        "name": "Nimal",
        "role": "USER"
      }
    ]
  }
}
```

---

## 4. Get all available employee skills

**Endpoint**

```http
GET /api/employees/skills
```

**Purpose**

Return all available skills without duplicates.

**Main Collection Used**

- `Set<String>`

**Expected Response**

```json
{
  "count": 5,
  "data": [
    "Java",
    "Spring Boot",
    "SQL",
    "React",
    "Docker"
  ]
}
```

---

## 5. Get order count by status

**Endpoint**

```http
GET /api/orders/status-count
```

**Purpose**

Return order counts grouped by status.

**Main Collection Used**

- `Map<String, Long>`

**Expected Response**

```json
{
  "data": {
    "PENDING": 12,
    "COMPLETED": 45,
    "CANCELLED": 3
  }
}
```

---

## 6. Get orders grouped by status

**Endpoint**

```http
GET /api/orders/by-status
```

**Purpose**

Return orders grouped by order status.

**Main Collection Used**

- `Map<String, List<OrderDto>>`

**Expected Response**

```json
{
  "data": {
    "PENDING": [
      {
        "orderId": 101,
        "customerName": "Anton",
        "totalAmount": 2500.00
      },
      {
        "orderId": 102,
        "customerName": "Nimal",
        "totalAmount": 1800.00
      }
    ],
    "COMPLETED": [
      {
        "orderId": 103,
        "customerName": "Kamal",
        "totalAmount": 3200.00
      }
    ],
    "CANCELLED": []
  }
}
```

---

## 7. Get product filter options

**Endpoint**

```http
GET /api/products/filter-options
```

**Purpose**

Return data needed for frontend filter dropdowns.

**Main Collections Used**

- `Set<String>` for unique brands
- `Set<String>` for unique categories

**Expected Response**

```json
{
  "data": {
    "brands": [
      "Sony",
      "Samsung",
      "LG"
    ],
    "categories": [
      "Electronics",
      "Home Appliances",
      "Accessories"
    ]
  }
}
```

---

## 8. Search products by filters

**Endpoint**

```http
POST /api/products/search
```

**Sample Request**

```json
{
  "category": "Electronics",
  "brands": ["Sony", "Samsung"],
  "minPrice": 10000,
  "maxPrice": 50000
}
```

**Purpose**

Search products using multiple filters.

**Main Collections Used**

- Request: `List<String>` for brands
- Response: `List<ProductDto>`

**Expected Response**

```json
{
  "count": 2,
  "data": [
    {
      "id": 201,
      "name": "Sony Headphones",
      "brand": "Sony",
      "category": "Electronics",
      "price": 25000
    },
    {
      "id": 202,
      "name": "Samsung Soundbar",
      "brand": "Samsung",
      "category": "Electronics",
      "price": 42000
    }
  ]
}
```

---

## 9. Get permissions by role

**Endpoint**

```http
GET /api/roles/{roleName}/permissions
```

**Example**

```http
GET /api/roles/ADMIN/permissions
```

**Purpose**

Return unique permissions assigned to a given role.

**Main Collection Used**

- `Set<String>`

**Expected Response**

```json
{
  "role": "ADMIN",
  "count": 4,
  "data": [
    "USER_READ",
    "USER_CREATE",
    "USER_UPDATE",
    "USER_DELETE"
  ]
}
```

---

## 10. Get role-permission matrix

**Endpoint**

```http
GET /api/roles/permission-matrix
```

**Purpose**

Return all roles mapped to their permissions.

**Main Collection Used**

- `Map<String, Set<String>>`

**Expected Response**

```json
{
  "data": {
    "ADMIN": [
      "USER_READ",
      "USER_CREATE",
      "USER_UPDATE",
      "USER_DELETE"
    ],
    "MANAGER": [
      "USER_READ",
      "USER_UPDATE"
    ],
    "VIEWER": [
      "USER_READ"
    ]
  }
}
```

---

## 11. Get status lookup values

**Endpoint**

```http
GET /api/lookups/statuses
```

**Purpose**

Return code-description mapping for statuses.

**Main Collection Used**

- `Map<String, String>`

**Expected Response**

```json
{
  "data": {
    "P": "Pending",
    "A": "Approved",
    "R": "Rejected"
  }
}
```

---

## 12. Get dashboard summary

**Endpoint**

```http
GET /api/dashboard/summary
```

**Purpose**

Return aggregated summary data for dashboard widgets.

**Main Collections Used**

- `Map<String, Long>`
- `Map<String, BigDecimal>`

**Expected Response**

```json
{
  "data": {
    "userCountByRole": {
      "ADMIN": 2,
      "MANAGER": 3,
      "USER": 18
    },
    "orderCountByStatus": {
      "PENDING": 5,
      "COMPLETED": 40,
      "CANCELLED": 2
    },
    "monthlySales": {
      "JAN": 250000.00,
      "FEB": 180000.00,
      "MAR": 320000.00
    }
  }
}
```

---

## 13. Get audit logs

**Endpoint**

```http
GET /api/audit/logs
```

**Purpose**

Return activity logs in ordered form.

**Main Collection Used**

- `List<AuditLogDto>`

**Expected Response**

```json
{
  "count": 3,
  "data": [
    {
      "id": 1,
      "action": "USER_CREATED",
      "performedBy": "admin",
      "time": "2026-04-08T10:00:00"
    },
    {
      "id": 2,
      "action": "ORDER_APPROVED",
      "performedBy": "manager1",
      "time": "2026-04-08T11:15:00"
    },
    {
      "id": 3,
      "action": "PRODUCT_UPDATED",
      "performedBy": "admin",
      "time": "2026-04-08T12:30:00"
    }
  ]
}
```

---

## 14. Get audit logs grouped by user

**Endpoint**

```http
GET /api/audit/logs/grouped-by-user
```

**Purpose**

Return audit logs grouped by username.

**Main Collection Used**

- `Map<String, List<AuditLogDto>>`

**Expected Response**

```json
{
  "data": {
    "admin": [
      {
        "id": 1,
        "action": "USER_CREATED",
        "time": "2026-04-08T10:00:00"
      },
      {
        "id": 3,
        "action": "PRODUCT_UPDATED",
        "time": "2026-04-08T12:30:00"
      }
    ],
    "manager1": [
      {
        "id": 2,
        "action": "ORDER_APPROVED",
        "time": "2026-04-08T11:15:00"
      }
    ]
  }
}
```

---

## 15. Process next notification

**Endpoint**

```http
POST /api/notifications/process-next
```

**Purpose**

Process the next pending notification using queue behavior.

**Main Collection Used**

- `Queue<NotificationDto>` internally
- Response can be a single object + summary

**Expected Response**

```json
{
  "message": "Next notification processed successfully",
  "processedNotification": {
    "id": 1,
    "type": "EMAIL",
    "message": "Payroll file is ready",
    "status": "PROCESSED"
  },
  "remainingCount": 4
}
```

---

# Suggested DTOs

You can design your DTOs around these simple models:

- `UserDto`
- `OrderDto`
- `ProductDto`
- `AuditLogDto`
- `NotificationDto`

You can also create request DTOs like:

- `ProductSearchRequest`

---

# Recommended Response Wrapper

To keep the API design professional and consistent, use a common response format.

## Example for list responses

```json
{
  "count": 2,
  "data": [
    {
      "id": 1,
      "name": "Anton"
    },
    {
      "id": 2,
      "name": "Nimal"
    }
  ]
}
```

## Example for grouped responses

```json
{
  "data": {
    "IT": [
      {
        "id": 1,
        "name": "Anton"
      }
    ],
    "HR": [
      {
        "id": 2,
        "name": "Nimal"
      }
    ]
  }
}
```

## Example for lookup responses

```json
{
  "data": {
    "A": "Approved",
    "R": "Rejected",
    "P": "Pending"
  }
}
```

---

# Implementation Order Recommendation

If you want to build them step by step, follow this order:

1. `GET /api/users`
2. `GET /api/users/roles`
3. `GET /api/users/grouped-by-department`
4. `GET /api/employees/skills`
5. `GET /api/orders/status-count`
6. `GET /api/orders/by-status`
7. `GET /api/lookups/statuses`
8. `GET /api/roles/{roleName}/permissions`
9. `GET /api/roles/permission-matrix`
10. `GET /api/products/filter-options`
11. `POST /api/products/search`
12. `GET /api/audit/logs`
13. `GET /api/audit/logs/grouped-by-user`
14. `GET /api/dashboard/summary`
15. `POST /api/notifications/process-next`

---