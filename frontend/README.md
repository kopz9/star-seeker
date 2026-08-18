# StarSeeker Frontend

A modern, type-safe React + TypeScript frontend for the StarSeeker artist management platform.

## 🎨 Features

- ✅ **Fully Typed TypeScript** - No `any` types, complete type safety
- ✅ **Modern UI with Tailwind CSS** - Beautiful gradient designs and animations
- ✅ **Authentication System** - Login and registration with JWT tokens
- ✅ **Artist Search** - Search and hire artists from Spotify
- ✅ **Form Validation** - Client-side and server-side validation handling
- ✅ **Error Handling** - Comprehensive error handling with user-friendly messages
- ✅ **Responsive Design** - Works on all screen sizes
- ✅ **Custom Animations** - Smooth transitions and micro-interactions

## 🚀 Getting Started

### Prerequisites

- Node.js 18+ and npm
- Your backend running on `http://localhost:8080`

### Installation

1. **Install dependencies:**
```bash
npm install
```

2. **Create environment file:**
```bash
cp .env.example .env
```

Edit `.env` if your backend runs on a different URL:
```env
VITE_API_BASE_URL=http://localhost:8080
```

3. **Start the development server:**
```bash
npm run dev
```

The app will be available at `http://localhost:3000`

### Build for Production

```bash
npm run build
```

The production build will be in the `dist/` directory.

## 📁 Project Structure

```
src/
├── components/          # React components
│   ├── LoginForm.tsx
│   ├── RegisterForm.tsx
│   ├── ArtistSearch.tsx
│   └── Navbar.tsx
├── pages/              # Page components
│   ├── AuthPage.tsx
│   └── Dashboard.tsx
├── hooks/              # Custom React hooks
│   └── useAuth.tsx
├── services/           # API client
│   └── api.ts
├── types/              # TypeScript type definitions
│   └── api.ts
├── App.tsx             # Main app component
├── main.tsx            # Entry point
└── index.css           # Global styles
```

## 🔧 Configuration

### API Client

The API client is configured in `src/services/api.ts` and automatically handles:
- JWT token management
- Error responses
- Validation errors
- Type-safe requests and responses

### Authentication

Authentication state is managed through React Context in `src/hooks/useAuth.tsx`:
- Automatic token persistence in localStorage
- Token expiration handling
- User session management

## 📝 TypeScript Types

All API types are defined in `src/types/api.ts`:

```typescript
interface User {
  id: number;
  email: string;
  username: string;
}

interface LoginRequest {
  username: string;
  password: string;
}

// ... and more
```

No `any` types are used anywhere in the codebase!

## 🎨 Styling

The project uses:
- **Tailwind CSS** for utility-first styling
- **Custom fonts**: Space Grotesk (body) and Syne (display)
- **CSS animations** for smooth transitions
- **Gradient effects** for modern aesthetics

### Color Scheme

- Primary: Purple (`from-purple-400 to-purple-600`)
- Secondary: Pink (`from-pink-500 to-pink-600`)
- Accent: Yellow for stars
- Background: Dark gray (`bg-gray-900`)

## 🔌 Connecting to Backend

The frontend expects the following backend endpoints:

### Authentication
- `POST /auth/login` - Login
- `POST /users` - Register

### Artists
- `GET /search?name={query}` - Search artists

### Contracts
- `POST /contracts` - Create contract (hire artist)
- `DELETE /contracts/{id}` - Delete contract

Make sure your backend is running and CORS is configured to allow requests from `http://localhost:3000`.

### CORS Configuration (Spring Boot)

Add this to your backend:

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}
```

## 🐛 Known Issues & TODOs

1. **Artist Hiring**: The current implementation is a placeholder. You'll need to:
   - Modify your backend to return artist IDs from the search endpoint
   - Or create artists on-the-fly when hiring
   - Update `ArtistSearch.tsx` component accordingly

2. **Contract Management**: Add a page to view and manage hired artists

3. **User Profile**: Add a profile page to view/edit user information

4. **Refresh Tokens**: Implement refresh token logic for better security

## 🧪 Development Tips

### Type Safety

The project is configured with strict TypeScript settings:
- `noImplicitAny: true`
- `strictNullChecks: true`
- No `any` types allowed

### Linting

Run ESLint:
```bash
npm run lint
```

### Hot Module Replacement

Vite provides instant HMR - your changes will reflect immediately without losing app state.

## 📦 Dependencies

### Core
- `react` - UI library
- `react-dom` - React DOM rendering
- `lucide-react` - Icon library

### Dev Tools
- `vite` - Build tool
- `typescript` - Type checking
- `tailwindcss` - Styling
- `eslint` - Linting

## 🚨 Security Notes

- JWT tokens are stored in localStorage (consider httpOnly cookies for production)
- Always use HTTPS in production
- Implement CSRF protection on the backend
- Set proper CORS policies

## 📄 License

This project is part of the StarSeeker application.

## 🤝 Contributing

1. Ensure all TypeScript types are properly defined
2. No `any` types allowed
3. Follow the existing code style
4. Test all forms and API interactions
5. Ensure responsive design works on all screen sizes

---

**Need help?** Check the backend API documentation or open an issue.
