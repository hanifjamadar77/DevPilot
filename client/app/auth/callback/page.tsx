"use client";

import { useRouter, useSearchParams } from "next/navigation";
import { useEffect } from "react";

import { useCurrentUser } from "@/hooks/use-auth";
import { Spinner } from "@/components/ui/spinner";

export default function AuthCallbackPage() {
    const router = useRouter();
    const params = useSearchParams();
    const token = params.get("token");
    const error = params.get("error");
    const { data: user, isLoading, isFetched } = useCurrentUser();

    useEffect(() => {
        if (error) {
            router.replace(`/login?error=${error}`);
            return;
        }

        if (token) {
            // Store token in localStorage
            localStorage.setItem("auth_token", token);
            // Trigger re-fetch of user data with token
            window.location.href = "/dashboard";
            return;
        }

        if (!isFetched || isLoading) return;

        if (user) {
            router.replace("/dashboard");
            return;
        }
        router.replace("/login?error=session");
    }, [user, isLoading, isFetched, error, token, router]);

    return (
        <div className="flex min-h-svh flex-col items-center justify-center gap-3">
            <Spinner className="size-6" />
            <p className="text-sm text-muted-foreground">Finishing GitHub sign-in ...</p>
        </div>
    );
}