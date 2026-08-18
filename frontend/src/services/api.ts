import type {
  LoginRequest,
  LoginResponse,
  RegisterRequest,
  User,
  SpotifySearchResponse,
  Contract,
  ContractResponse,
  AppException,
  ValidationError,
} from '../types/api';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

class ApiError extends Error {
  constructor(
    public statusCode: number,
    message: string,
    public validationErrors?: ValidationError
  ) {
    super(message);
    this.name = 'ApiError';
  }
}

async function handleResponse<T>(response: Response): Promise<T> {
  if (!response.ok) {
    const contentType = response.headers.get('content-type');
    
    if (contentType?.includes('application/json')) {
      const error = await response.json();
      
      // Check if it's a validation error (map of field: message)
      if (typeof error === 'object' && !error.message) {
        throw new ApiError(response.status, 'Validation failed', error as ValidationError);
      }
      
      // Check if it's an AppException
      if (error.message && error.statusCode) {
        throw new ApiError(error.statusCode, error.message);
      }
    }
    
    throw new ApiError(response.status, `HTTP ${response.status}: ${response.statusText}`);
  }

  const contentType = response.headers.get('content-type');
  if (contentType?.includes('application/json')) {
    return response.json();
  }
  
  return {} as T;
}

function getAuthHeader(token: string | null): HeadersInit {
  if (!token) return {};
  return {
    'Authorization': `Bearer ${token}`,
  };
}

export const api = {
  // Auth endpoints
  async login(credentials: LoginRequest): Promise<LoginResponse> {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    });
    return handleResponse<LoginResponse>(response);
  },

  async register(data: RegisterRequest): Promise<User> {
    const response = await fetch(`${API_BASE_URL}/users`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
    return handleResponse<User>(response);
  },

  // Artist endpoints
  async searchArtists(query: string): Promise<SpotifySearchResponse[]> {
    const response = await fetch(
      `${API_BASE_URL}/search?name=${encodeURIComponent(query)}`
    );
    return handleResponse<SpotifySearchResponse[]>(response);
  },

  // Contract endpoints
  async createContract(artistId: number, token: string): Promise<Contract> {
    const response = await fetch(`${API_BASE_URL}/contracts`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        ...getAuthHeader(token),
      },
      body: JSON.stringify({ artistId }),
    });
    return handleResponse<Contract>(response);
  },

  async getMyContracts(token: string): Promise<ContractResponse[]> {
    const response = await fetch(`${API_BASE_URL}/contracts`, {
      headers: getAuthHeader(token),
    });
    return handleResponse<ContractResponse[]>(response);
  },

  async deleteContract(contractId: number, token: string): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/contracts/${contractId}`, {
      method: 'DELETE',
      headers: getAuthHeader(token),
    });
    return handleResponse<void>(response);
  },

  // User endpoints
  async deleteUser(userId: number, token: string): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/users/${userId}`, {
      method: 'DELETE',
      headers: getAuthHeader(token),
    });
    return handleResponse<void>(response);
  },
};

export { ApiError };
