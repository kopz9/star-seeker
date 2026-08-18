// API Response Types
export interface User {
  id: number;
  email: string;
  username: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  expiresIn: number;
  id: number;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

export interface Artist {
  id: number;
  uri: string;
  name: string;
  imageUrl: string;
}

export interface SpotifySearchResponse {
  id: number;
  name: string;
  imageUrl: string;
}

export interface Contract {
  id: number;
  artistId: number;
  userId: number;
  artist?: Artist;
}

export interface ContractResponse{
  id: number;
  artistId: number;
  artistName: string;
  artistImageUrl: string;
  userId: number;
}

export interface ContractWithArtist extends Contract {
  artist: Artist;
}

export interface AppException {
  message: string;
  statusCode: number;
}

export interface ValidationError {
  [field: string]: string;
}

// Auth Context Types
export interface AuthContextType {
  user: User | null;
  token: string | null;
  login: (username: string, password: string) => Promise<void>;
  register: (username: string, email: string, password: string) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
  isLoading: boolean;
}
