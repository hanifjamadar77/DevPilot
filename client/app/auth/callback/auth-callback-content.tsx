"use client";

import { useRouter, useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";

import { useCurrentUser } from "@/hooks/use-auth";
import { Spinner } from "@/components/ui/spinner";

export function AuthCallbackContent() {
  const router = useRouter();
  const params = useSearchParams();
  const token = params.get("token");
  const error = params.get("error");
  const [tokenStored, setTokenStored] = useState(false);
  const { data: user, isLoading, isFetched } = useCurrentUser({ enabled: tokenStored });

  useEffect(() => {
    // Handle error from OAuth
    if (error) {
      console.error("OAuth error:", error);
      router.push(`/login?error=${error}`);
      return;
    }

    // Extract and store token from URL
    if (token && !tokenStored) {
      console.log("Token received, storing in localStorage");
      localStorage.setItem("auth_token", token);
      // Enable user fetch after storing token
      setTokenStored(true);
      return;
    }

    // After token is stored and user data is fetched
    if (tokenStored && isFetched) {
      if (user) {
        console.log("User authenticated, redirecting to dashboard");
        router.push("/dashboard");
      } else {
        console.log("User fetch failed, redirecting to login");
        router.push("/login?error=unauthorized");
      }
    }
  }, [token, error, tokenStored, isFetched, user, router]);

  return (
    <div className="flex min-h-svh flex-col items-center justify-center gap-3">
      <Spinner className="size-6" />
      <p className="text-sm text-muted-foreground">Finishing GitHub sign-in ...</p>
    </div>
  );
}
